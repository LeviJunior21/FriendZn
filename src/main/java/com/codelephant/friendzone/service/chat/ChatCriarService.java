package com.codelephant.friendzone.service.chat;

import com.codelephant.friendzone.dto.chat.MensagemPostPutRequestDTO;
import com.codelephant.friendzone.model.Mensagem;

@FunctionalInterface
public interface ChatCriarService {
    Mensagem salvar(MensagemPostPutRequestDTO mensagemPostPutRequestDTO);
}
