package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.chat.MensagemPostPutRequestDTO;
import com.codelephant.friendzone.model.Mensagem;
import com.codelephant.friendzone.service.chat.ChatCriarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Casos de teste para socket")
public class MensagemV1ControllerTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc driver;
    Mensagem mensagem;
    MensagemPostPutRequestDTO mensagemPostPutRequestDTO;

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());

        mensagem = Mensagem.builder()
                .data(new Date())
                .conteudo("Ola")
                .remetente(1L)
                .receptor(2L)
                .build();

        mensagemPostPutRequestDTO = MensagemPostPutRequestDTO.builder()
                .data(new Date())
                .conteudo("Ola")
                .remetente(1L)
                .receptor(2L)
                .build();

        driver = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @DisplayName("Quando tentamos gravar uma mensagem.")
    public void testGravarMensagem1() throws Exception {
        ResultActions result = driver.perform(post("/ws/app/private-message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mensagemPostPutRequestDTO)))
                .andDo(print());
    }
}
