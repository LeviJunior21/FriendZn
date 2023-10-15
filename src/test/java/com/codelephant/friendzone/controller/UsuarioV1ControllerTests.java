package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.usuario.UsuarioDTO;
import com.codelephant.friendzone.dto.usuario.UsuarioPostPutRequestDTO;
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
                    .build();
        }

        @AfterEach
        void tearDown() {
            usuarioRepository.deleteAll();
            publicacaoRepository.deleteAll();
        }

        @Test
        @DisplayName("Quando salvamos um usuário.")
        void quandoSalvamosUmUsuario() throws Exception {
            String responseJSONString = driver.perform(post(URI_USUARIOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(usuarioPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();

            UsuarioDTO usuarioDTO = objectMapper.readValue(responseJSONString, UsuarioDTO.class);

            assertAll(
                    () -> assertEquals(usuarioPostPutRequestDTO.getApelido(), usuarioDTO.getApelido()),
                    () -> assertEquals(usuarioPostPutRequestDTO.getEmail(), usuarioRepository.findAll().stream().findFirst().get().getEmail())
            );
        }
    }
}
