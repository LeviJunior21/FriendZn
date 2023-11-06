package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.usuario.UsuarioPostPutRequestDTO;
import com.codelephant.friendzone.service.usuario.UsuarioCriarService;
import com.codelephant.friendzone.service.usuario.UsuarioListarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/v1/usuarios",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UsuarioV1Controller {

    @Autowired
    UsuarioCriarService usuarioCriarService;
    @Autowired
    UsuarioListarService usuarioListarService;

    @PostMapping()
    public ResponseEntity<?> salvarUsuario(
            @RequestBody @Valid UsuarioPostPutRequestDTO usuarioPostPutRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioCriarService.salvar(usuarioPostPutRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<?> getUsuarios() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioListarService.listar(null));
    }

}