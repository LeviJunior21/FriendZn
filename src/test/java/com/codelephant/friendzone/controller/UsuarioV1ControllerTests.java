package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.usuario.*;
import com.codelephant.friendzone.exception.CustomErrorType;
import com.codelephant.friendzone.model.LoginType;
import com.codelephant.friendzone.model.SexoSelecionado;
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
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
        UsuarioApelidoDescricaoPutRequestDTO usuarioApelidoDescricaoPutRequestDTO;
        UsuarioEmojiPatchRequestDTO usuarioEmojiPatchRequestDTO;
        UsuarioValidarDTO usuarioValidarDTO;
        Usuario usuario;

        @BeforeEach
        void setup() {
            objectMapper.registerModule(new JavaTimeModule());

            usuario = Usuario.builder()
                    .email("levi.lima@estudante.ufcg.edu.br")
                    .codigoAcesso(123456L)
                    .apelido("Levi")
                    .publicacoes(new ArrayList<>())
                    .gostaram(new HashSet<>())
                    .idade(22)
                    .sexo(SexoSelecionado.MASCULINO)
                    .loginType(LoginType.GitHub)
                    .naoGostaram(new HashSet<>())
                    .build();

            usuarioPostPutRequestDTO = UsuarioPostPutRequestDTO.builder()
                    .apelido("Levi")
                    .email("levi.pereira.junior@ccc.ufcg.edu.br")
                    .codigoAcesso(123456L)
                    .loginType(LoginType.GitHub)
                    .idade(22)
                    .sexo(SexoSelecionado.MASCULINO)
                    .build();

            usuarioValidarDTO = UsuarioValidarDTO.builder()
                    .email("levi.lima@estudante.ufcg.edu.br")
                    .idGoogle(12345678)
                    .codigoAcesso(123456L)
                    .build();

            usuarioApelidoDescricaoPutRequestDTO = UsuarioApelidoDescricaoPutRequestDTO.builder()
                    .codigoAcesso(123456L)
                    .descricao("Nova descricao")
                    .apelido("Levi")
                    .build();

            usuarioEmojiPatchRequestDTO = UsuarioEmojiPatchRequestDTO.builder()
                    .emoji("ok")
                    .codigoAcesso(usuario.getCodigoAcesso())
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
        @DisplayName("Quando tentamos salvar um usuário com o nome vazio.")
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
        @DisplayName("Quando tentamos salvar um usuário com o nome null.")
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
        @DisplayName("Quando verificamos a existencia do usuario.")
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
        @DisplayName("Quando validamos as informações do usuário.")
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
        @DisplayName("Quando verificamos a Inexistência do usuário.")
        void quandoVerificamosInExistenciaDoUsuario() throws Exception {
            // Arrange
            String responseJSONString = driver.perform(get(URI_USUARIOS + "/validar/usuario?email=email@exemplo.com")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(usuarioPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType customErrorType = objectMapper.readValue(responseJSONString, CustomErrorType.class);
            assertEquals("O usuario com esse id nao existe.", customErrorType.getMessage());
        }

        @Test
        @DisplayName("Quando verificamos a invalidade das informações do código de acesso ao usuário.")
        void quandoVerificamosAInvalidadeDasInformacoesDoCodigoDeAcessoAoUsuario() throws Exception {
            // Arrange
            usuarioValidarDTO.setCodigoAcesso(123489L);

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
        @DisplayName("Quando validamos a invalidade das informações do email ao usuário.")
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

        @Test
        @DisplayName("Quando buscamos por um usuário específico pelo ID.")
        void quandoBuscamosPorUmUsuarioEspecificoPeloID() throws Exception {
            String responseJSONString = driver.perform(get(URI_USUARIOS + "/usuario/" + usuario.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            UsuarioDTO usuarioDTO = objectMapper.readValue(responseJSONString, UsuarioDTO.class);

            assertAll(
                () -> assertEquals(usuario.getApelido(), usuarioDTO.getApelido())
            );
        }

        @Test
        @DisplayName("QuandoAlteramosADescricaoDoUsuario")
        void quandoAlteramosADescricaoDoUsuario() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            driver.perform(put(URI_USUARIOS + "/alterar-apelido-descricao/usuario?id=" + usuario.getId())
                            .content(objectMapper.writeValueAsString(usuarioApelidoDescricaoPutRequestDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertEquals(usuarioApelidoDescricaoPutRequestDTO.getDescricao(), usuarioRepository.findAll().stream().findFirst().get().getDescricao());
        }

        @Test
        @DisplayName("QuandoAlteramosOApeldioDoUsuarioPorApelidoVazio")
        void quandoAlteramosOApelidoDoUsuarioPorApelidoVazio() throws Exception {
            // Arrange
            usuarioApelidoDescricaoPutRequestDTO.setApelido("");

            // Act
            String responseJSONString = driver.perform(put(URI_USUARIOS + "/alterar-apelido-descricao/usuario?id=" + usuario.getId())
                            .content(objectMapper.writeValueAsString(usuarioApelidoDescricaoPutRequestDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType customErrorType = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Apelido invalido, ele nao pode ser vazio.", customErrorType.getErrors().get(0));
            assertEquals("Erros de validacao encontrados", customErrorType.getMessage());
        }

        @Test
        @DisplayName("QuandoAlteramosOApelidoDoUsuarioPorApelidoNula")
        void quandoAlteramosOApelidoDoUsuarioPorApelidoNulo() throws Exception {
            // Arrange
            usuarioApelidoDescricaoPutRequestDTO.setApelido(null);

            // Act
            String responseJSONString = driver.perform(put(URI_USUARIOS + "/alterar-apelido-descricao/usuario?id=" + usuario.getId())
                            .content(objectMapper.writeValueAsString(usuarioApelidoDescricaoPutRequestDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType customErrorType = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", customErrorType.getMessage());
            assertEquals("Apelido invalido, ele nao pode ser vazio.", customErrorType.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando verificamos a existencia do usuario com o ID do Github.")
        void quandoVerificamosAExistenciaDoUsuarioComIdGithub() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            String responseJSONString = driver.perform(get(URI_USUARIOS + "/github?idAuth=" + usuario.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Boolean result = objectMapper.readValue(responseJSONString, Boolean.class);

            // Assert
            assertEquals(true, result);
        }

        @Test
        @DisplayName("Quando verificamos a existencia do usuario com o ID do Github.")
        void quandoVerificamosAInexistenciaDoUsuarioComIdGithub() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            String responseJSONString = driver.perform(get(URI_USUARIOS + "/github?idAuth=" + 10L)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Boolean result = objectMapper.readValue(responseJSONString, Boolean.class);

            // Assert
            assertEquals(false, result);
        }

        @Test
        @DisplayName("Quando buscamos um usuário pelo ID do Auth do GitHub")
        void quandoBuscamosUmUsuarioPeloIDDoAuthDoGitHub() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            String responseJSONString = driver.perform(get(URI_USUARIOS + "/github/" + usuario.getCodigoAcesso())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            UsuarioDTO usuarioDTO = objectMapper.readValue(responseJSONString, UsuarioDTO.class);

            // Assert
            assertEquals(usuario.getId(), usuarioDTO.getId());
        }

        @Test
        @DisplayName("Quando deletamos o usuário do banco de dados.")
        void quandoDeletamosOUsuarioDobancoDeDados() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup.

            // Act
            driver.perform(delete(URI_USUARIOS + "/id/" + usuario.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertEquals(0, usuarioRepository.findAll().size());
        }

        @Test
        @DisplayName("Quando buscamos o perdil do usuário pelo ID")
        void quandoBuscamosOPerdilDoUsuarioPeloID() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            String responseJSONString = driver.perform(get(URI_USUARIOS + "/perfil/" + usuario.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            UsuarioPerfilDTO usuarioPerfilDTO = objectMapper.readValue(responseJSONString, UsuarioPerfilDTO.class);

            // Assert
            assertEquals(usuario.getApelido(), usuarioPerfilDTO.getApelido());
        }

        @Test
        @DisplayName("Quando alteramos o emoji do usuário.")
        void quandoAlteramosOEmojiDoUsuario() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            String responseJSONString = driver.perform(patch(URI_USUARIOS + "/usuario/" + usuario.getId() + "/emoji")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(usuarioEmojiPatchRequestDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertEquals(usuarioEmojiPatchRequestDTO.getEmoji(), responseJSONString);
        }
    }
}
