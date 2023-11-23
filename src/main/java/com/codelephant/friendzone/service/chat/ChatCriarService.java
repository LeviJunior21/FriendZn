package com.codelephant.friendzone.service.chat;

import com.codelephant.friendzone.dto.chat.ChatPostPutRequestDTO;
import com.codelephant.friendzone.model.Mensagem;

@FunctionalInterface
public interface ChatCriarService {
    Mensagem salvar(ChatPostPutRequestDTO mensagemPostPutRequestDTO);
}
