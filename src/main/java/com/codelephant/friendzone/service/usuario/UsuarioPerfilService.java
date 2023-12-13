package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.dto.usuario.UsuarioPerfilDTO;

@FunctionalInterface
public interface UsuarioPerfilService {
    UsuarioPerfilDTO buscar(Long id);
}
