package com.codelephant.friendzone.service.chat;

import com.codelephant.friendzone.dto.chat.ChatPostPutRequestDTO;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Chat;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.ChatRepository;
import com.codelephant.friendzone.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatListarPadraoService implements ChatListarService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ChatPostPutRequestDTO> listar(Long idUsuarioInscrito) {
        List<Chat> chats = chatRepository.findByIDDestinatario(idUsuarioInscrito);
        List<ChatPostPutRequestDTO> chatPostPutRequestDTOS = chats.stream().map(chat -> modelMapper.map(chat, ChatPostPutRequestDTO.class)).collect(Collectors.toList());
        chats.forEach(chat -> chatRepository.deleteById(chat.getId()));
        return chatPostPutRequestDTOS;
    }
}
