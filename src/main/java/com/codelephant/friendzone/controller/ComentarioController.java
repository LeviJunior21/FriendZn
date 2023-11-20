package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.comentario.ComentarioDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioPostPutRequestDTO;
import com.codelephant.friendzone.service.comentario.ComentarioCriarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ComentarioController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    ComentarioCriarService comentarioCriarService;

    @MessageMapping("/comentarios.sendMessage/{idPublicacao}")
    @SendTo("/topic/public/{idPublicacao}")
    public ComentarioDTO sendComentario(
            @Payload ComentarioPostPutRequestDTO comentarioPostPutRequestDTO,
            @DestinationVariable Long idPublicacao
    ){
        try {
            ComentarioDTO comentario = comentarioCriarService.salvar(comentarioPostPutRequestDTO, idPublicacao);
            return comentario;
        } catch (Exception e) {
            return ComentarioDTO.builder().build();
        }
    }
}
