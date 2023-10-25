package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.dto.usuario.UsuarioDTO;

import java.util.List;

@FunctionalInterface
public interface UsuarioListarService {
    List<UsuarioDTO> listar(Long id);
}
