package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.chat.ChatPostPutRequestDTO;
import com.codelephant.friendzone.service.chat.ChatArmazenarService;
import com.codelephant.friendzone.service.chat.ChatListarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private SimpUserRegistry simpUserRegistry;
    @Autowired
    ChatListarService chatListarService;
    @Autowired
    ChatArmazenarService chatArmazenarService;

    @MessageMapping("/private-message")
    public ChatPostPutRequestDTO chat(@Payload ChatPostPutRequestDTO chatPostPutRequestDTO) {
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatPostPutRequestDTO.getReceptor()), "/private", chatPostPutRequestDTO);
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatPostPutRequestDTO.getRemetente()), "/private", chatPostPutRequestDTO);

        String receptorUsername = String.valueOf(chatPostPutRequestDTO.getReceptor());
        if (!isUserOnline(receptorUsername)) {
            chatArmazenarService.armazenar(chatPostPutRequestDTO);
        }

        return chatPostPutRequestDTO;
    }

    private boolean isUserOnline(String username) {
        return simpUserRegistry.getUser(username) != null;
    }
}
