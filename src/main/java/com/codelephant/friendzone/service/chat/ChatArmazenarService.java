package com.codelephant.friendzone.service.chat;

import com.codelephant.friendzone.dto.chat.ChatPostPutRequestDTO;

@FunctionalInterface
public interface ChatArmazenarService {
    void armazenar(ChatPostPutRequestDTO chatPostPutRequestDTO);
}
