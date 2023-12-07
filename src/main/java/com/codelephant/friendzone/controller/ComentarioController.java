package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.comentario.ComentarioDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoPostRequestDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioPostPutRequestDTO;
import com.codelephant.friendzone.service.comentario.ComentarioCriarService;
import com.codelephant.friendzone.service.comentario.ComentarioGostarService;
import com.codelephant.friendzone.service.comentario.ComentarioNaoGostarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ComentarioController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    ComentarioCriarService comentarioCriarService;
    @Autowired
    ComentarioGostarService comentarioGostarService;
    @Autowired
    ComentarioNaoGostarService comentarioNaoGostarService;

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

    @MessageMapping("/curtir-comentario/publicacao/{idPublicacao}/comentario/{idComentario}")
    public ComentarioGostarOuNaoDTO gostarComentario(
            @Payload ComentarioGostarOuNaoPostRequestDTO comentarioGostarOuNaoPostRequestDTO,
            @DestinationVariable Long idPublicacao,
            @DestinationVariable Long idComentario
    ){
        ComentarioGostarOuNaoDTO comentarioGostarOuNaoDTO = ComentarioGostarOuNaoDTO.builder().gostou(0).naoGostou(0).build();
        try {
            if (comentarioGostarOuNaoPostRequestDTO.getGostar().equals(1L)) {
                comentarioGostarOuNaoDTO = comentarioGostarService.gostar(
                        comentarioGostarOuNaoPostRequestDTO,
                        comentarioGostarOuNaoPostRequestDTO.getIdPublicacao(),
                        comentarioGostarOuNaoPostRequestDTO.getIdComentario()
                );
            } else {
                comentarioGostarOuNaoDTO = comentarioNaoGostarService.naoGostar(
                        comentarioGostarOuNaoPostRequestDTO,
                        comentarioGostarOuNaoPostRequestDTO.getIdPublicacao(),
                        comentarioGostarOuNaoPostRequestDTO.getIdComentario()
                );
            }
        } catch(Exception e) {}

        simpMessagingTemplate.convertAndSend("/topic/public/publicacao/" + idPublicacao + "/comentario/" + idComentario, comentarioGostarOuNaoDTO);
        return comentarioGostarOuNaoDTO;
    }
}
