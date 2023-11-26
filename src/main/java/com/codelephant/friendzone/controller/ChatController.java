package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.chat.ChatPostPutRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import java.util.stream.Collectors;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @MessageMapping("/private-message")
    public ChatPostPutRequestDTO chat(@Payload ChatPostPutRequestDTO chatPostPutRequestDTO) {
        System.out.println("Mensagem recebida: " + chatPostPutRequestDTO.getMensagem());
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatPostPutRequestDTO.getReceptor()), "/private", chatPostPutRequestDTO);
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatPostPutRequestDTO.getRemetente()), "/private", chatPostPutRequestDTO);
        return chatPostPutRequestDTO;
    }
}
