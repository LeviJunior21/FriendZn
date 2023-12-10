package com.codelephant.friendzone.service.chat;

import com.codelephant.friendzone.dto.chat.ChatPostPutRequestDTO;

import java.util.List;

@FunctionalInterface
public interface ChatListarService {
    List<ChatPostPutRequestDTO> listar(Long idUsuarioInscrito);
}
