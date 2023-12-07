package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.model.LoginType;

@FunctionalInterface
public interface UsuarioValidarService {
    boolean validarIdAuth(Long idGithub, LoginType loginType);
}
