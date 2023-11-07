package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.usuario.UsuarioDTO;
import com.codelephant.friendzone.dto.usuario.UsuarioPostPutRequestDTO;
import com.codelephant.friendzone.dto.usuario.UsuarioValidarDTO;
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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        UsuarioValidarDTO usuarioValidarDTO;
        Usuario usuario;

        @BeforeEach
        void setup() {
            objectMapper.registerModule(new JavaTimeModule());

            usuario = Usuario.builder()
                    .email("levi.lima@estudante.ufcg.edu.br")
                    .codigoAcesso(123456)
                    .apelido("Levi")
                    .publicacoes(new ArrayList<>())
                    .gostaram(new ArrayList<>())
                    .naoGostaram(new ArrayList<>())
                    .build();

            usuarioPostPutRequestDTO = UsuarioPostPutRequestDTO.builder()
                    .apelido("Levi")
                    .email("levi.pereira.junior@ccc.ufcg.edu.br")
                    .codigoAcesso(123456)
                    .build();

            usuarioValidarDTO = UsuarioValidarDTO.builder()
                    .email("levi.lima@estudante.ufcg.edu.br")
                    .idGoogle(12345678)
                    .codigoAcesso(123456)
                    .build();

            usuario = usuarioRepository.save(usuario);
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
                    () -> assertEquals(usuarioPostPutRequestDTO.getApelido(), usuarioDTO.getApelido())
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

        @Test
        @DisplayName("Quando tentamos salvar um usuário com email null.")
        void quandoVerificamosExistenciaDoUsuario() throws Exception {
            // Arrange
            String responseJSONString = driver.perform(get(URI_USUARIOS + "/validar/usuario?email=" + usuario.getEmail())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(usuarioPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Boolean response = objectMapper.readValue(responseJSONString, Boolean.class);
            assertEquals(true, response);
        }

        @Test
        @DisplayName("Quando tentamos salvar um usuário com email null.")
        void quandoValidamosInformacoesDoUsuario() throws Exception {
            // Arrange
            String responseJSONString = driver.perform(get(URI_USUARIOS + "/usuario")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(usuarioValidarDTO)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            UsuarioDTO usuarioDTO = objectMapper.readValue(responseJSONString, UsuarioDTO.class);
            assertEquals("Levi", usuarioDTO.getApelido());
        }

        @Test
        @DisplayName("Quando tentamos salvar um usuário com email null.")
        void quandoVerificamosInExistenciaDoUsuario() throws Exception {
            // Arrange
            String responseJSONString = driver.perform(get(URI_USUARIOS + "/validar/usuario?email=email@exemplo.com")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(usuarioPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Boolean response = objectMapper.readValue(responseJSONString, Boolean.class);
            assertEquals(false, response);
        }

        @Test
        @DisplayName("Quando tentamos salvar um usuário com email null.")
        void quandoValidamosAInvalidadeDasInformacoesDoCodigoDeAcessoAoUsuario() throws Exception {
            // Arrange
            usuarioValidarDTO.setCodigoAcesso(123489);

            String responseJSONString = driver.perform(get(URI_USUARIOS + "/usuario")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(usuarioValidarDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType customErrorType = objectMapper.readValue(responseJSONString, CustomErrorType.class);
            assertEquals("O codigo de acesso eh diferente.", customErrorType.getMessage());
        }

        @Test
        @DisplayName("Quando tentamos salvar um usuário com email null.")
        void quandoValidamosAInvalidadeDasInformacoesDoEmailAoUsuario() throws Exception {
            // Arrange
            usuarioValidarDTO.setEmail("email@gmail.com");

            String responseJSONString = driver.perform(get(URI_USUARIOS + "/usuario")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(usuarioValidarDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType customErrorType = objectMapper.readValue(responseJSONString, CustomErrorType.class);
            assertEquals("O usuario com esse id nao existe.", customErrorType.getMessage());
        }
    }
}
