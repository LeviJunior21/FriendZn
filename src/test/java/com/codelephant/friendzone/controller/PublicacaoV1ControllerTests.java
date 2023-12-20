package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.publicacao.PublicacaoDTO;
import com.codelephant.friendzone.dto.publicacao.PublicacaoPostPutRequestDTO;
import com.codelephant.friendzone.model.LoginType;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.model.SexoSelecionado;
import com.codelephant.friendzone.service.publicacao.PublicacaoCriarService;
import com.codelephant.friendzone.service.publicacao.PublicacaoListarSeguindoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.codelephant.friendzone.exception.CustomErrorType;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import com.codelephant.friendzone.repository.UsuarioRepository;
import com.codelephant.friendzone.utils.Categoria;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Casos de testes para publicação.")
public class PublicacaoV1ControllerTests {

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
        @Autowired
        PublicacaoCriarService publicacaoCriarService;
        @Autowired
        PublicacaoListarSeguindoService publicacaoListarSeguindoService;


        PublicacaoPostPutRequestDTO publicacaoPostPutRequestDTO;
        Usuario usuario;
        Publicacao publicacao;

        @BeforeEach
        void setup() {
            objectMapper.registerModule(new JavaTimeModule());
            publicacaoPostPutRequestDTO = PublicacaoPostPutRequestDTO.builder()
                    .publicacao("Ola!")
                    .codigoAcesso(123456L)
                    .date(new Date())
                    .categoria(Categoria.amizade)
                    .build();

            Usuario usuarioTemp = Usuario.builder()
                    .apelido("Levi")
                    .email("levi.pereira.junior@ccc.ufcg.edu.br")
                    .codigoAcesso(123456L)
                    .publicacoes(new ArrayList<>())
                    .loginType(LoginType.GitHub)
                    .idade(22)
                    .sexo(SexoSelecionado.MASCULINO)
                    .build();

            usuario = usuarioRepository.save(usuarioTemp);

            Set<Usuario> interessados = new HashSet<>();
            interessados.add(usuario);
            publicacaoRepository.save(
                    Publicacao.builder()
                            .id(10L)
                            .date(new Date())
                            .categoria(Categoria.amizade)
                            .usuario(usuario)
                            .publicacao("Ola!")
                            .interessados(interessados)
                            .comentarios(new ArrayList<>())
                            .build()
            );
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
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();

            PublicacaoDTO publicacaoDTO = objectMapper.readValue(responseJSONString, PublicacaoDTO.class);

            // Assert
            assertAll(
                    ()-> assertEquals(1, usuarioRepository.findAll().size()),
                    ()-> assertEquals(1, usuarioRepository.findById(usuario.getId()).get().getPublicacoes().size()),
                    ()-> assertEquals("Ola!", usuarioRepository.findById(usuario.getId()).get().getPublicacoes().stream().findFirst().get().getPublicacao()),
                    ()-> assertEquals(2, publicacaoRepository.findAll().size()),
                    ()-> assertEquals("Levi", publicacaoDTO.getUsuario().getApelido()),
                    ()-> assertEquals("Ola!", publicacaoRepository.findAll().stream().findFirst().get().getPublicacao())
            );
        }

        @Test
        @DisplayName("Quando listamos todas as publicações existentes.")
        void quandoListamosTodasAsPublicacoesExistentes() throws Exception {
            // Arrange
            publicacaoRepository.save(Publicacao.builder()
                            .categoria(Categoria.amizade)
                            .date(new Date())
                            .comentarios(new ArrayList<>())
                            .publicacao("algo")
                            .usuario(usuario)
                            .build()
            );

            // Act
            String responseJSONString = driver.perform(get(URI_PUBLICACOES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(publicacaoPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            List<PublicacaoDTO> resultado = objectMapper.readValue(responseJSONString, new TypeReference<List<PublicacaoDTO>>() {});
            assertEquals(2, resultado.size());
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

        @Test
        @DisplayName("Quando salvamos uma publicação para uma publicacao vazia.")
        void quandoSalvamosUmaPublicacaoParaUmaPublicacaoVazia() throws Exception {
            // Arrange
            publicacaoPostPutRequestDTO.setPublicacao("");

            // Act
            String responseJSONString = driver.perform(post(URI_PUBLICACOES + "/publicacao?id=" + 100L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(publicacaoPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            CustomErrorType customErrorType = objectMapper.readValue(responseJSONString, CustomErrorType.class);
            assertEquals("Erros de validacao encontrados", customErrorType.getMessage());
            assertEquals("Publicacao invalida.", customErrorType.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando salvamos uma publicação para uma publicacao null.")
        void quandoSalvamosUmaPublicacaoParaUmaPublicacaoNull() throws Exception {
            // Arrange
            publicacaoPostPutRequestDTO.setPublicacao(null);

            // Act
            String responseJSONString = driver.perform(post(URI_PUBLICACOES + "/publicacao?id=" + 100L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(publicacaoPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            CustomErrorType customErrorType = objectMapper.readValue(responseJSONString, CustomErrorType.class);
            assertEquals("Publicacao invalida.", customErrorType.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando salvamos uma publicação para um usuário válido mas código diferente.")
        void quandoSalvamosUmaPublicacaoParaUmUsuarioValidoMasCodigoDiferente() throws Exception {
            // Arrange
            publicacaoPostPutRequestDTO.setCodigoAcesso(12346L);

            // Act
            String responseJSONString = driver.perform(post(URI_PUBLICACOES + "/publicacao?id=" + usuario.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(publicacaoPostPutRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            CustomErrorType customErrorType = objectMapper.readValue(responseJSONString, CustomErrorType.class);
            assertEquals("O codigo de acesso eh diferente.", customErrorType.getMessage());
        }

        @Test
        @DisplayName("Quando listamos as publicações interessadas pelo usuario.")
        void quandoListamosAsPublicacoesInteressadasPeloUsuario() throws Exception {
            // Arrange
            // Nenhuma necessidade além do setup

            // Act
            String responseJSONString = driver.perform(get(URI_PUBLICACOES + "/seguindo/" + usuario.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            List<PublicacaoDTO> publicacoes = objectMapper.readValue(responseJSONString, new TypeReference<ArrayList<PublicacaoDTO>>() {});

            // Assert
            assertAll(
                    () -> assertEquals(0, publicacoes.size())
            );
        }

    }
}
