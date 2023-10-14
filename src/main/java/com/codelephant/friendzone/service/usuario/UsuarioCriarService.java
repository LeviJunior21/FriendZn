package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.dto.usuario.UsuarioDTO;
import com.codelephant.friendzone.dto.usuario.UsuarioPostPutRequestDTO;

@FunctionalInterface
public interface UsuarioCriarService {
    UsuarioDTO salvar(UsuarioPostPutRequestDTO usuarioPostPutRequestDTO);
}
