package com.codelephant.friendzone.service.usuario;

@FunctionalInterface
public interface UsuarioExistenciaService {
    Boolean verificar(String email);
}
