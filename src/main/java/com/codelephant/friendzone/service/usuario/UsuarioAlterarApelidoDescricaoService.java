package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.dto.usuario.UsuarioApelidoDescricaoPutRequestDTO;

@FunctionalInterface
public interface UsuarioAlterarApelidoDescricaoService {
    void alterarDescricao(UsuarioApelidoDescricaoPutRequestDTO usuarioDescricaoPutRequestDTO, Long idUsuario);
}
