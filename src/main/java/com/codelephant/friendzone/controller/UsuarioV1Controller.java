package com.codelephant.friendzone.controller;

import com.codelephant.friendzone.dto.usuario.UsuarioApelidoDescricaoPutRequestDTO;
import com.codelephant.friendzone.dto.usuario.UsuarioPostPutRequestDTO;
import com.codelephant.friendzone.dto.usuario.UsuarioValidarDTO;
import com.codelephant.friendzone.model.LoginType;
import com.codelephant.friendzone.service.usuario.*;
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
    @Autowired
    UsuarioValidarInformacoesService usuarioValidarInformacoesService;
    @Autowired
    UsuarioAlterarApelidoDescricaoService usuarioAlterarDescricaoService;
    @Autowired
    UsuarioGetByIdGitHubService usuarioGetByIdGitHubService;
    @Autowired
    UsuarioDeletarService usuarioDeletarService;
    @Autowired
    UsuarioPerfilService usuarioPerfilService;

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
                .body(usuarioValidarInformacoesService.validarInformacoes(usuarioValidarDTO));
    }

    @GetMapping("/validar/usuario")
    public ResponseEntity<?> validarEmail(
            @RequestParam String email
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioExistenciaService.verificar(email));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioListarService.listar(id).get(0));
    }

    @PutMapping("/alterar-apelido-descricao/usuario")
    public ResponseEntity<?> alterarDescricao(
            @RequestParam Long id,
            @RequestBody @Valid UsuarioApelidoDescricaoPutRequestDTO usuarioDescricaoPutRequestDTO
    ) {
      usuarioAlterarDescricaoService.alterarDescricao(usuarioDescricaoPutRequestDTO, id);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body("");
    }

    @GetMapping("/github")
    public ResponseEntity<?> verificarExistenciaGithub(
            @RequestParam Long idAuth
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioValidarService.validarIdAuth(idAuth, LoginType.GitHub));
    }

    @GetMapping("/google")
    public ResponseEntity<?> verificarExistenciaGoogle(
            @RequestParam Long idAuth
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioValidarService.validarIdAuth(idAuth, LoginType.Google));
    }

    @GetMapping("/github/{idAuth}")
    public ResponseEntity<?> getUSuario(
            @PathVariable Long idAuth
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioGetByIdGitHubService.getIDServer(idAuth));
    }

    @DeleteMapping("/id/{idUsuario}")
    public ResponseEntity<?> deletarUsuario(
            @PathVariable Long idUsuario
    ) {
        usuarioDeletarService.deletar(idUsuario);
        return ResponseEntity
               .status(HttpStatus.OK)
               .body("");
    }

    @GetMapping("/perfil/{id}")
    public ResponseEntity<?> getPerfil(
            @PathVariable Long id
    ) {
       return ResponseEntity
               .status(HttpStatus.OK)
               .body(usuarioPerfilService.buscar(id));
    }
}
