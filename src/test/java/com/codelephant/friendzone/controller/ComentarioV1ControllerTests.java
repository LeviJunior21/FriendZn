package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.comentario.ComentarioPostPutRequestDTO;
import com.codelephant.friendzone.dto.publicacao.PublicacaoPostPutRequestDTO;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import com.codelephant.friendzone.repository.UsuarioRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    ObjectMapper objectMapper;

    String URI_COMENTARIOS = "/v1/comentarios";


    @Nested
    @DisplayName("Caso de testes para a API Rest Full;.")
    class TestTestFull {

        Usuario usuario;
        PublicacaoPostPutRequestDTO publicacaoPostPutRequestDTO;
        ComentarioPostPutRequestDTO comentarioPostPutRequestDTO;

        @BeforeEach
        void setup() {
            objectMapper.registerModule(new JavaTimeModule());
            publicacaoPostPutRequestDTO = PublicacaoPostPutRequestDTO.builder()
                    .publicacao("Ola!")
                    .date(new Date())
                    .build();

            Usuario usuarioTemp = Usuario.builder()
                    .apelido("Levi")
                    .publicacoes(new ArrayList<>())
                    .build();

            Publicacao publicacao = Publicacao.builder()
                    .publicacao("Ola!")
                    .comentarios(new ArrayList<>())
                    .date(new Date())
                    .build();
            usuarioTemp.getPublicacoes().add(publicacao);

            usuario = usuarioRepository.save(usuarioTemp);

            comentarioPostPutRequestDTO = ComentarioPostPutRequestDTO.builder()
                    .comentario("Oi")
                    .build();
        }

        @AfterEach
        void tearDown() {
            usuarioRepository.deleteAll();
        }

        @Test
        @DisplayName("quando comentamos numa publicação existente de um usuário")
        void  quandoComentamosNumaPublicacaoExistenteDeUmUsuario() throws Exception {
            driver.perform(post(URI_COMENTARIOS + "/" + usuario.getPublicacoes().stream().findFirst().get().getId() + "/usuario?idUsuario=" + usuario.getId())
                    .content(objectMapper.writeValueAsString(comentarioPostPutRequestDTO))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();

            assertEquals("Oi", usuarioRepository.findById(usuario.getId()).stream().findFirst().get().getPublicacoes().stream().findFirst().get().getComentarios().stream().findFirst().get().getComentario());
            assertEquals(1, publicacaoRepository.findAll().size());
            usuarioRepository.deleteById(usuario.getId());
            assertEquals(0, publicacaoRepository.findAll().size());
        }
    }
}
