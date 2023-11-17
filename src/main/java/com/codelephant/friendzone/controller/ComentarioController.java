package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.comentario.ComentarioDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioPostPutRequestDTO;
import com.codelephant.friendzone.service.comentario.ComentarioCriarService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @MessageMapping("/comentarios.sendMessage")
    @SendTo("/topic/public")
    public ComentarioPostPutRequestDTO sendComentario(@Payload ComentarioPostPutRequestDTO comentarioPostPutRequestDTO){
        try {
            comentarioCriarService.salvar(comentarioPostPutRequestDTO);
            System.out.println("Comentario salvo com sucesso");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return comentarioPostPutRequestDTO;
    }
}
