package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.publicacao.PublicacaoPostPutRequestDTO;
import com.codelephant.friendzone.service.publicacao.PublicacaoCriarService;
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

    @PostMapping("/publicacao")
    public ResponseEntity<?> salvarPublicacao(
            @RequestBody @Valid PublicacaoPostPutRequestDTO publicacaoPostPutRequestDTO,
            @RequestParam Long id
    ) {
        publicacaoCriarService.salvar(publicacaoPostPutRequestDTO, id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }
}
