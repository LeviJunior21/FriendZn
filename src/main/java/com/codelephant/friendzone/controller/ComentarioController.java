package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.comentario.ComentarioDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioPostPutRequestDTO;
import com.codelephant.friendzone.service.comentario.ComentarioCriarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
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

    @MessageMapping("/chat/message")
    @SendTo("/chat")
    public ComentarioDTO gravarMensagem(ComentarioPostPutRequestDTO comentarioPostPutRequestDTO){
        System.out.println(comentarioPostPutRequestDTO.getComentario());
        List<ComentarioDTO> comentario = comentarioCriarService.salvar(comentarioPostPutRequestDTO, 1L, 1L);
        return comentario.stream().findFirst().get();
    }
}
