package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.comentario.ComentarioDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoPostRequestDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioPostPutRequestDTO;
import com.codelephant.friendzone.model.Comentario;
import com.codelephant.friendzone.model.LoginType;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.ComentarioRepository;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import com.codelephant.friendzone.repository.UsuarioRepository;
import com.codelephant.friendzone.utils.Categoria;
import com.codelephant.friendzone.utils.WSConfig;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private MockMvc driver;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PublicacaoRepository publicacaoRepository;
    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebSocketStompClient webSocketStompClient;
    @LocalServerPort
    private int port;
    private final String URI_COMENTARIOS = "/v1/comentarios";

    public String getWsComentatarios() {
        return "ws://localhost:" + port + "/ws";
    }

    @Nested
    @DisplayName("Caso de testes para a API Rest Full;.")
    class TestTestFull {

        private Usuario usuario;
        private Publicacao publicacao;
        private Comentario comentario;
        private AtomicReference<String> mensagemRecebida;
        private ComentarioPostPutRequestDTO comentarioPostPutRequestDTO;
        private ComentarioGostarOuNaoPostRequestDTO comentarioGostarOuNaoPostRequestDTO;

        @BeforeEach
        void setup() {
            objectMapper.registerModule(new JavaTimeModule());

            usuario = Usuario.builder()
                    .apelido("Levi")
                    .email("levi.pereira.junior@ccc.ufcg.edu.br")
                    .codigoAcesso(123456L)
                    .idAuth(1111L)
                    .loginType(LoginType.GitHub)
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
                    .codigoAcesso(123456L)
                    .publicacao(publicacao)
                    .timestamp(new Date())
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
            mensagemRecebida = new AtomicReference<String>();

            comentarioPostPutRequestDTO = ComentarioPostPutRequestDTO.builder()
                    .comentario("Oi")
                    .codigoAcesso(123456L)
                    .idUsuario(usuario.getId())
                    .timestamp(new Date())
                    .build();

            comentarioGostarOuNaoPostRequestDTO = ComentarioGostarOuNaoPostRequestDTO.builder()
                    .codigoAcesso(123456L)
                    .idUsuario(usuario.getId())
                    .idPublicacao(1L)
                    .idComentario(1L)
                    .gostar(1L)
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
            driver.perform(post(URI_COMENTARIOS + "/" + 1L + "/usuario?idUsuario=" + 1L)
                    .content(objectMapper.writeValueAsString(comentarioPostPutRequestDTO))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

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
            String responseJSONString = driver.perform(get(URI_COMENTARIOS + "/publicacao/" + publicacao.getId()
                    ).contentType(MediaType.APPLICATION_JSON))
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
        @DisplayName("Quando gostamos de um comentário")
        void quandoGostamosDeUmComentarioExistente() throws Exception {
            // Arrange
            // Nnehuma necessidade além do setup.

            // Act
            driver.perform(post(URI_COMENTARIOS + "/gostar/publicacao/" + publicacao.getId() + "/comentario?id=" + comentario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(comentarioGostarOuNaoPostRequestDTO)))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertAll(
                    () -> assertEquals(1, publicacaoRepository.findById(publicacao.getId()).get().getComentarios().stream().findFirst().get().getGostaram().size()),
                    () -> assertEquals(0, publicacaoRepository.findById(publicacao.getId()).get().getComentarios().stream().findFirst().get().getNaoGostaram().size())
            );
        }

        @Test
        @DisplayName("Quando não gostamos de um comentário")
        void quandoNaoGostamosDeUmComentarioExistente() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            driver.perform(post(URI_COMENTARIOS + "/nao-gostar/publicacao/" + publicacao.getId() + "/comentario?id=" + comentario.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(comentarioGostarOuNaoPostRequestDTO)))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertAll(
                    () -> assertEquals(1, publicacaoRepository.findById(publicacao.getId()).get().getComentarios().stream().findFirst().get().getNaoGostaram().size()),
                    () -> assertEquals(0, publicacaoRepository.findById(publicacao.getId()).get().getComentarios().stream().findFirst().get().getGostaram().size())
            );
        }
        
        @Test
        @DisplayName("Quando enviamos um comentário ao WebSocket")
        void quandoEnviamosUmComentarioAoWebSocket() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            WSConfig comentarioWSConfig = WSConfig.builder()
                    .webSocketStompClient(webSocketStompClient)
                    .ws(getWsComentatarios())
                    .subscribe("/topic/public/" + publicacao.getId())
                    .destination("/app/comentarios.sendMessage/" + publicacao.getId())
                    .data(objectMapper.writeValueAsString(comentarioPostPutRequestDTO))
                    .build();
            comentarioWSConfig.runSendAndReceive(mensagemRecebida);

            // Assert
            ComentarioDTO comentarioDTO = objectMapper.readValue(mensagemRecebida.get(), ComentarioDTO.class);
            assertAll(
                    () -> assertEquals(null, comentarioDTO.getComentario())
            );
        }

        @Test
        @DisplayName("Quando gostamos comentario usando WebSocket.")
        void quandoGostamosDoComentarioUsandoWebSocket() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            WSConfig comentarioWSConfig = WSConfig.builder()
                    .webSocketStompClient(webSocketStompClient)
                    .ws(getWsComentatarios())
                    .destination("/app/curtir-comentario/publicacao/" + publicacao.getId() + "/comentario/" + comentario.getId())
                    .subscribe("/topic/public/publicacao/" + publicacao.getId() + "/comentario/" + comentario.getId())
                    .data(objectMapper.writeValueAsString(comentarioGostarOuNaoPostRequestDTO))
                    .build();
            comentarioWSConfig.runSendAndReceive(mensagemRecebida);

            // Assert
            ComentarioGostarOuNaoDTO comentarioGostarOuNaoDTO = objectMapper.readValue(mensagemRecebida.get(), ComentarioGostarOuNaoDTO.class);
            assertAll(
                    () -> assertEquals(0, comentarioGostarOuNaoDTO.getGostou()),
                    () -> assertEquals(0, comentarioGostarOuNaoDTO.getNaoGostou())
            );
        }

        @Test
        @DisplayName("Quando buscamos pelas curtidas do comentario")
        void quandoBuscamosPelasCurtidasDoUsuario() throws Exception {
            String responseJSONString = driver.perform(get(URI_COMENTARIOS + "/publicacao/" + publicacao.getId() + "/comentario/" + comentario.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            ComentarioGostarOuNaoDTO comentarioGostarOuNaoDTO = objectMapper.readValue(responseJSONString, ComentarioGostarOuNaoDTO.class);

            assertAll(
                    () -> assertEquals(0, comentarioGostarOuNaoDTO.getGostou()),
                    () -> assertEquals(0, comentarioGostarOuNaoDTO.getNaoGostou())
            );
        }
    }
}
