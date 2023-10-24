package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.usuario.UsuarioDTO;
import com.codelephant.friendzone.dto.usuario.UsuarioPostPutRequestDTO;
import com.codelephant.friendzone.exception.CustomErrorType;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Casos de testes para usuário.")
public class UsuarioV1ControllerTests {

    @Autowired
    MockMvc driver;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PublicacaoRepository publicacaoRepository;
    @Autowired
    ObjectMapper objectMapper;

    String URI_USUARIOS = "/v1/usuarios";

    @Nested
    @DisplayName("Casos de teste para a API Rest Full")
    class TestRestFull {

        UsuarioPostPutRequestDTO usuarioPostPutRequestDTO;

        @BeforeEach
        void setup() {
            objectMapper.registerModule(new JavaTimeModule());
            usuarioPostPutRequestDTO = UsuarioPostPutRequestDTO.builder()
                    .apelido("Levi")
                    .email("levi.pereira.junior@ccc.ufcg.edu.br")
                    .codigoAcesso(123456)
                    .build();
        }

        @AfterEach
        void tearDown() {
            usuarioRepository.deleteAll();
            publicacaoRepository.deleteAll();
        }

        @Test
        @DisplayName("Quando salvamos um usuário com sucesso.")
        void quandoSalvamosUmUsuario() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            String responseJSONString = driver.perform(post(URI_USUARIOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(usuarioPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();

            UsuarioDTO usuarioDTO = objectMapper.readValue(responseJSONString, UsuarioDTO.class);

            // Assert
            assertAll(
                    () -> assertEquals(usuarioPostPutRequestDTO.getApelido(), usuarioDTO.getApelido()),
                    () -> assertEquals(usuarioPostPutRequestDTO.getEmail(), usuarioRepository.findAll().stream().findFirst().get().getEmail())
            );
        }

        @Test
        @DisplayName("Quando tentamos salvar um usuário com nome vazio.")
        void quandoSalvamosUmUsuarioComNomeVazio() throws Exception {
            // Arrange
            usuarioPostPutRequestDTO.setApelido("");

            // Act
            String responseJSONString = driver.perform(post(URI_USUARIOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(usuarioPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType customErrorType = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", customErrorType.getMessage());
            assertEquals("Apelido invalido.", customErrorType.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando tentamos salvar um usuário com nome null.")
        void quandoSalvamosUmUsuarioComNomeNull() throws Exception {
            // Arrange
            usuarioPostPutRequestDTO.setApelido(null);

            // Act
            String responseJSONString = driver.perform(post(URI_USUARIOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(usuarioPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType customErrorType = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", customErrorType.getMessage());
            assertEquals("Apelido invalido.", customErrorType.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando tentamos salvar um usuário com email vazio.")
        void quandoSalvamosUmUsuarioComEmailVazio() throws Exception {
            // Arrange
            usuarioPostPutRequestDTO.setEmail("");

            // Act
            String responseJSONString = driver.perform(post(URI_USUARIOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(usuarioPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType customErrorType = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", customErrorType.getMessage());
            assertEquals("Email invalido.", customErrorType.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando tentamos salvar um usuário com email null.")
        void quandoSalvamosUmUsuarioComEmailNull() throws Exception {
            // Arrange
            usuarioPostPutRequestDTO.setEmail(null);

            // Act
            String responseJSONString = driver.perform(post(URI_USUARIOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(usuarioPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType customErrorType = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", customErrorType.getMessage());
            assertEquals("Email invalido.", customErrorType.getErrors().get(0));
        }
    }
}
