package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.dto.usuario.UsuarioDTO;

@FunctionalInterface
public interface UsuarioGetByIdGitHubService {
    UsuarioDTO getIDServer(Long idAuth);
}
