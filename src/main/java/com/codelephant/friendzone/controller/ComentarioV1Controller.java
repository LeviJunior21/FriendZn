package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.comentario.ComentarioPostPutRequestDTO;
import com.codelephant.friendzone.service.comentario.ComentarioCriarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/v1/comentarios",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ComentarioV1Controller {

    @Autowired
    ComentarioCriarService comentarioCriarService;

    @PostMapping("/{idPublicacao}/usuario")
    public ResponseEntity<?> salvarPublicacao(
            @RequestBody @Valid ComentarioPostPutRequestDTO comentarioPostPutRequestDTO,
            @PathVariable Long idPublicacao,
            @RequestParam Long idUsuario
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(comentarioCriarService.salvar(comentarioPostPutRequestDTO, idPublicacao, idUsuario));
    }
}
