package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.usuario.UsuarioPostPutRequestDTO;
import com.codelephant.friendzone.dto.usuario.UsuarioValidarDTO;
import com.codelephant.friendzone.service.usuario.UsuarioCriarService;
import com.codelephant.friendzone.service.usuario.UsuarioExistenciaService;
import com.codelephant.friendzone.service.usuario.UsuarioListarService;
import com.codelephant.friendzone.service.usuario.UsuarioValidarService;
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
    @Autowired
    UsuarioValidarService usuarioValidarService;
    @Autowired
    UsuarioExistenciaService usuarioExistenciaService;

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

    @GetMapping("/usuario")
    public ResponseEntity<?> validarUsuario(
            @RequestBody @Valid UsuarioValidarDTO usuarioValidarDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioValidarService.validarInformacoes(usuarioValidarDTO));
    }

    @GetMapping("/validar/usuario")
    public ResponseEntity<?> validarEmail(
            @RequestParam String email
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioExistenciaService.verificar(email));
    }
}
