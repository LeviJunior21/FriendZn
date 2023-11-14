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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Casos de testes para publicação.")
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

    String URI_COMENTARIOS = "/v1/comentarios";

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
    }
}
