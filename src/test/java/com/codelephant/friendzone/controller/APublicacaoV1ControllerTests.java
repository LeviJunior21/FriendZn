package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.publicacao.PublicacaoPostPutRequestDTO;
import com.codelephant.friendzone.exception.CustomErrorType;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Casos de testes para publicação.")
public class APublicacaoV1ControllerTests {

    @Autowired
    MockMvc driver;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PublicacaoRepository publicacaoRepository;

    String URI_PUBLICACOES = "/v1/publicacoes";

    @Nested
    @DisplayName("Casos de teste para a API Rest Full")
    class TestRestFull {

        @Autowired
        UsuarioRepository usuarioRepository;

        PublicacaoPostPutRequestDTO publicacaoPostPutRequestDTO;
        Usuario usuario;

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

            usuario = usuarioRepository.save(usuarioTemp);
        }

        @AfterEach
        void tearDown() {
            usuarioRepository.deleteAll();
            publicacaoRepository.deleteAll();
        }

        @Test
        @DisplayName("Quando salvamos uma publicação para um usuário existente.")
        void quandoSalvamosUmaPublicacaoParaUmUsuarioExistente() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            String responseJSONString = driver.perform(post(URI_PUBLICACOES + "/publicacao?id=" + usuario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(publicacaoPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertEquals(1, usuarioRepository.findAll().size());
            assertEquals(1, usuarioRepository.findById(usuario.getId()).get().getPublicacoes().size());
            assertEquals("Ola!", usuarioRepository.findById(usuario.getId()).get().getPublicacoes().stream().findFirst().get().getPublicacao());
            assertEquals(1, publicacaoRepository.findAll().size());
            assertEquals("Ola!", publicacaoRepository.findAll().stream().findFirst().get().getPublicacao());
            assertEquals(1, publicacaoRepository.findAll().stream().findFirst().get().getId());
        }

        @Test
        @DisplayName("Quando salvamos uma publicação para um usuário inexistente.")
        void quandoSalvamosUmaPublicacaoParaUmUsuarioInexistente() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            String responseJSONString = driver.perform(post(URI_PUBLICACOES + "/publicacao?id=" + 100L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(publicacaoPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            CustomErrorType customErrorType = objectMapper.readValue(responseJSONString, CustomErrorType.class);
            assertEquals("O usuario com esse id nao existe.", customErrorType.getMessage());
        }
    }
}
