package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.publicacao.PublicacaoPostPutRequestDTO;
import com.codelephant.friendzone.service.publicacao.PublicacaoCriarService;
import com.codelephant.friendzone.service.publicacao.PublicacaoListarSeguindoService;
import com.codelephant.friendzone.service.publicacao.PublicacaoListarService;
import com.codelephant.friendzone.service.publicacao.PublicacaoUsuarioEspecificoListarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(
        value = "/v1/publicacoes",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PublicacaoV1Controller {
    @Autowired
    PublicacaoCriarService publicacaoCriarService;
    @Autowired
    PublicacaoListarService publicacaoListarService;
    @Autowired
    PublicacaoListarSeguindoService publicacaoListarSeguindoService;
    @Autowired
    PublicacaoUsuarioEspecificoListarService publicacaoUsuarioEspecificoListarService;

    @PostMapping("/publicacao")
    public ResponseEntity<?> salvarPublicacao(
            @RequestBody @Valid PublicacaoPostPutRequestDTO publicacaoPostPutRequestDTO,
            @RequestParam Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(publicacaoCriarService.salvar(publicacaoPostPutRequestDTO, id));
    }

    @GetMapping()
    public ResponseEntity<?> listarTodasPublicacoes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(publicacaoListarService.listar(null));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> listarUmaPublicacoes(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(publicacaoUsuarioEspecificoListarService.listar(id));
    }

    @GetMapping("/seguindo/{idUsuario}")
    public ResponseEntity<?> publicacoesSeguidas(
            @PathVariable Long idUsuario
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(publicacaoListarSeguindoService.listar(idUsuario));
    }
}
