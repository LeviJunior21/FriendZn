package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.comentario.ComentarioDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioPostPutRequestDTO;
import com.codelephant.friendzone.model.Comentario;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.ComentarioRepository;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import com.codelephant.friendzone.repository.UsuarioRepository;
import com.codelephant.friendzone.utils.Categoria;
import com.codelephant.friendzone.utils.ComentarioWSConfig;
import com.codelephant.friendzone.utils.ReceiveComentario;
import com.codelephant.friendzone.utils.SendComentario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@DisplayName("Casos de testes para publicação.")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ComentarioV1ControllerTests {

    @Autowired
    MockMvc driver;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PublicacaoRepository publicacaoRepository;
    @Autowired
    ComentarioRepository comentarioRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WebSocketStompClient webSocketStompClient;

    String URI_COMENTARIOS = "/v1/comentarios";

    @LocalServerPort
    private int port;

    public String getWsComentatarios() {
        return "ws://localhost:" + port + "/ws";
    }

    @Nested
    @DisplayName("Caso de testes para a API Rest Full;.")
    class TestTestFull {

        Usuario usuario;
        ComentarioPostPutRequestDTO comentarioPostPutRequestDTO;
        Publicacao publicacao;
        Comentario comentario;

        @BeforeEach
        void setup() {
            objectMapper.registerModule(new JavaTimeModule());

            usuario = Usuario.builder()
                    .apelido("Levi")
                    .email("levi.pereira.junior@ccc.ufcg.edu.br")
                    .codigoAcesso(123456)
                    .build();

            publicacao = Publicacao.builder()
                    .usuario(usuario)
                    .publicacao("Oi")
                    .date(new Date())
                    .categoria(Categoria.amizade)
                    .build();

            comentario = Comentario.builder()
                    .comentario("Ola")
                    .usuario(usuario)
                    .codigoAcesso(123456)
                    .publicacao(publicacao)
                    .build();
            List<Comentario> comentarios = new ArrayList<>();
            comentarios.add(comentario);
            publicacao.setComentarios(comentarios);

            List<Publicacao> publicacoes = new ArrayList<>();
            publicacoes.add(publicacao);
            usuario.setPublicacoes(publicacoes);

            usuario = usuarioRepository.save(usuario);
            publicacao = publicacaoRepository.save(publicacao);
            comentario = comentarioRepository.save(comentario);

            comentarioPostPutRequestDTO = ComentarioPostPutRequestDTO.builder()
                    .comentario("Oi")
                    .codigoAcesso(123456)
                    .idUsuario(usuario.getId())
                    .idPublicacao(publicacao.getId())
                    .build();
        }

        @AfterEach
        void tearDown() {
            usuarioRepository.deleteAll();
            publicacaoRepository.deleteAll();
            comentarioRepository.deleteAll();
        }

        @Test
        @DisplayName("quando comentamos numa publicação existente de um usuário")
        void quandoComentamosNumaPublicacaoExistenteDeUmUsuario() throws Exception {
            driver.perform(post(URI_COMENTARIOS + "/" + usuario.getPublicacoes().stream().findFirst().get().getId() + "/usuario?idUsuario=" + usuario.getId())
                    .content(objectMapper.writeValueAsString(comentarioPostPutRequestDTO))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();

            assertEquals("Ola", usuarioRepository.findById(usuario.getId()).stream().findFirst().get().getPublicacoes().stream().findFirst().get().getComentarios().stream().findFirst().get().getComentario());
            assertEquals(1, publicacaoRepository.findAll().size());
            usuarioRepository.deleteById(usuario.getId());
            assertEquals(0, publicacaoRepository.findAll().size());
        }

        @Test
        @DisplayName("quando comentamos numa publicação existente de um usuário")
        void quandoListamosComentariosDeUmaPublicacao() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup.

            // Act
            String responseJSONString = driver.perform(get(URI_COMENTARIOS + "/publicacao/" + publicacao.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            List<ComentarioDTO> resultado = objectMapper.readValue(responseJSONString, new TypeReference<List<ComentarioDTO>>() {});

            // Assert
            assertAll(
                () -> assertEquals(1, resultado.size()),
                () -> assertEquals("Ola", resultado.stream().findFirst().get().getComentario())
            );
        }

        @Test
        @DisplayName("Quando conectamos ao servidor usando WebSocket")
        void quandoConectamosAoServidorUsandoWebSocket() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            AtomicReference<String> mensagemRecebida = new AtomicReference<String>();
            StompSession stompSession = webSocketStompClient.connect(getWsComentatarios(), new StompSessionHandlerAdapter() {
                @Override
                public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                    System.out.println("Conectado ao servidor WebSocket com sucesso!");

                    session.subscribe("/topic/public", new StompFrameHandler() {
                        @Override
                        public Type getPayloadType(StompHeaders headers) {
                            return byte[].class;
                        }

                        @Override
                        public void handleFrame(StompHeaders headers, Object payload) {
                            mensagemRecebida.set(new String((byte[]) payload));
                            System.out.println("Mensagem recebida: " + mensagemRecebida.get());
                        }
                    });
                }
            }).get(5, TimeUnit.SECONDS);

            stompSession.send("/app/comentarios.sendMessage", objectMapper.writeValueAsString(comentarioPostPutRequestDTO));
            Thread.sleep(6000);

            System.out.println("Mensagem recebida no teste: " + mensagemRecebida.get());

            // Assert
            assertNotNull(mensagemRecebida.get());

            if (StringUtils.hasText(mensagemRecebida.get())) {
                ComentarioDTO comentarioDTOResponse = objectMapper.readValue(mensagemRecebida.get(), ComentarioDTO.class);
                assertEquals(comentarioPostPutRequestDTO.getComentario(), comentarioDTOResponse.getComentario());
            } else {
                fail("A mensagem recebida está nula ou vazia.");
            }
        }

        @Test
        @DisplayName("Caso de teste da minha API")
        void casoDeTesteDaMinhaApi() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            Semaphore barrier = new Semaphore(0);
            AtomicReference<String> mensagemRecebida = new AtomicReference<String>();
            ReceiveComentario threadReceiveComentario = ReceiveComentario.builder()
                    .webSocketUrl(getWsComentatarios())
                    .webSocketStompClient(webSocketStompClient)
                    .messageReceive(mensagemRecebida)
                    .subscribe("/topic/public")
                    .semaphore(barrier)
                    .build();
            Thread threadReceive = new Thread(threadReceiveComentario);
            threadReceive.start();

            barrier.acquire();

            SendComentario threadSendComentario = SendComentario.builder()
                    .data(objectMapper.writeValueAsString(comentarioPostPutRequestDTO))
                    .destination("/app/comentarios.sendMessage")
                    .stompSession(threadReceiveComentario.getStompSession())
                    .build();
            Thread threadSend = new Thread(threadSendComentario);
            threadSend.start();

            Thread.sleep(threadReceiveComentario.getTimeOutAndIncrement(1000));

            try {
                ComentarioDTO comentarioDTO = objectMapper.readValue(mensagemRecebida.get(), ComentarioDTO.class);
                assertEquals(comentarioPostPutRequestDTO.getComentario(), comentarioDTO.getComentario());
            } catch(Exception e) {
                System.out.println("Ocorreu um erro ao mapear para ComentarioDTO.\n\nErro:\n\n" + e.getMessage());
            }
        }

        @Test
        @DisplayName("Quando testamos outra Api")
        void quandoTestamosOutraApi() throws Exception {
            ComentarioWSConfig comentarioWSConfig = ComentarioWSConfig.builder()
                    .webSocketStompClient(webSocketStompClient)
                    .ws(getWsComentatarios())
                    .subscribe("/topic/public")
                    .destination("/app/comentarios.sendMessage")
                    .data(objectMapper.writeValueAsString(comentarioPostPutRequestDTO))
                    .build();

            AtomicReference<String> mensagemRecebida = new AtomicReference<String>();
            comentarioWSConfig.run(mensagemRecebida);

            ComentarioDTO comentarioDTO = objectMapper.readValue(mensagemRecebida.get(), ComentarioDTO.class);
            assertEquals(comentarioPostPutRequestDTO.getComentario(), comentarioDTO.getComentario());

        }
    }
}
