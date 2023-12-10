package com.codelephant.friendzone.service.chat;

import com.codelephant.friendzone.dto.chat.ChatPostPutRequestDTO;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Chat;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.ChatRepository;
import com.codelephant.friendzone.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ChatArmazenarPadraoService implements ChatArmazenarService {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public void armazenar(ChatPostPutRequestDTO chatPostPutRequestDTO) {
        Chat chat = modelMapper.map(chatPostPutRequestDTO, Chat.class);
        chatRepository.save(chat);
    }
}
