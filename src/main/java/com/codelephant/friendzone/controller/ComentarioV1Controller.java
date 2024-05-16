package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoPostRequestDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioPostPutRequestDTO;
import com.codelephant.friendzone.service.comentario.*;
import jakarta.validation.Constraint;
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
    @Autowired
    ComentarioListarService comentarioListarService;
    @Autowired
    ComentarioGostarService comentarioGostarService;
    @Autowired
    ComentarioNaoGostarService comentarioNaoGostarService;
    @Autowired
    ComentarioGetStatusService comentarioGetStatusService;

    @PostMapping("/{idPublicacao}/usuario")
    public ResponseEntity<?> salvarPublicacao(
            @RequestBody @Valid ComentarioPostPutRequestDTO comentarioPostPutRequestDTO,
            @PathVariable Long idPublicacao
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(comentarioCriarService.salvar(comentarioPostPutRequestDTO, idPublicacao));
    }

    @GetMapping("/publicacao/{idPublicacao}")
    public ResponseEntity<?> listarComentarios(
            @PathVariable Long idPublicacao
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comentarioListarService.listar(idPublicacao));
    }

    @PostMapping("/gostar/publicacao/{idPublicacao}/comentario")
    public ResponseEntity<?> gostar(
            @RequestBody @Valid ComentarioGostarOuNaoPostRequestDTO comentarioGostarOuNaoPostRequestDTO,
            @PathVariable Long idPublicacao,
            @RequestParam Long id
    ) {
        comentarioGostarService.gostar(comentarioGostarOuNaoPostRequestDTO, idPublicacao, id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("");
    }

    @PostMapping("/nao-gostar/publicacao/{idPublicacao}/comentario")
    public ResponseEntity<?> naoGostar(
            @RequestBody @Valid ComentarioGostarOuNaoPostRequestDTO comentarioGostarOuNaoPostRequestDTO,
            @PathVariable Long idPublicacao,
            @RequestParam Long id
    ) {
        comentarioNaoGostarService.naoGostar(comentarioGostarOuNaoPostRequestDTO, idPublicacao, id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("");
    }

    @GetMapping("/publicacao/{idPublicacao}/comentario/{idComentario}")
    public ResponseEntity<?> getStatusGostarNaoGostar(
            @PathVariable Long idPublicacao,
            @PathVariable Long idComentario
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comentarioGetStatusService.get(idPublicacao, idComentario));
    }
}
