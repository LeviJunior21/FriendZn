package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.chat.ChatPostPutRequestDTO;
import com.codelephant.friendzone.utils.WSConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

@Transactional
@DirtiesContext
@AutoConfigureMockMvc
@DisplayName("Casos de testes para o chat.")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatControllerTests {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebSocketStompClient webSocketStompClient;
    @LocalServerPort
    private int port;
    ChatPostPutRequestDTO chatPostPutRequestDTO;
    private AtomicReference<String> mensagemRecebida;

    public String getWsChat() {
        return "ws://localhost:" + port + "/ws";
    }

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        mensagemRecebida = new AtomicReference<String>();

        chatPostPutRequestDTO = ChatPostPutRequestDTO.builder()
                .mensagem("Ola")
                .timestamp(new Date())
                .remetente(1L)
                .receptor(2L)
                .build();
    }

    @Test
    @DisplayName("Quando enviamos uma mensagem para um usuário.")
    void quandoEnviamosUmaMensagemParaUmUsuario() throws Exception {
        WSConfig wsConfig = WSConfig.builder()
                .webSocketStompClient(webSocketStompClient)
                .ws(getWsChat())
                .data(objectMapper.writeValueAsString(chatPostPutRequestDTO))
                .destination("/app/privateChat")
                .build();
        wsConfig.runJustSend();
    }

    @Test
    @DisplayName("Quando enviamos uma mensagem para um usuário especifico.")
    void quandoEnviamosUmaMensagemParaUmUsuarioEspecifico() throws Exception {
        WSConfig wsConfig = WSConfig.builder()
                .webSocketStompClient(webSocketStompClient)
                .ws(getWsChat())
                .data(objectMapper.writeValueAsString(chatPostPutRequestDTO))
                .destination("/app/private-message")
                .subscribe("/user/" + chatPostPutRequestDTO.getReceptor() + "/private")
                .build();
        wsConfig.runSendAndReceive(mensagemRecebida);

        ChatPostPutRequestDTO chatTeste = objectMapper.readValue(mensagemRecebida.get(),ChatPostPutRequestDTO.class);

        assertAll(
                () -> assertEquals(chatPostPutRequestDTO.getMensagem(), chatTeste.getMensagem())
        );
    }

}
