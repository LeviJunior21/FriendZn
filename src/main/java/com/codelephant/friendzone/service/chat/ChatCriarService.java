package com.codelephant.friendzone.service.chat;

import com.codelephant.friendzone.dto.chat.ChatPostPutRequestDTO;

@FunctionalInterface
public interface ChatCriarService {
    ChatPostPutRequestDTO salvar(ChatPostPutRequestDTO mensagemPostPutRequestDTO);
}
