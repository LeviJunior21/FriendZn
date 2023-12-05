package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.dto.usuario.UsuarioDescricaoPutRequestDTO;

@FunctionalInterface
public interface UsuarioAlterarDescricaoService {
    void alterarDescricao(UsuarioDescricaoPutRequestDTO usuarioDescricaoPutRequestDTO, Long idUsuario);
}
