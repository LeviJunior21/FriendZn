package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.dto.usuario.UsuarioDTO;
import com.codelephant.friendzone.dto.usuario.UsuarioValidarDTO;

@FunctionalInterface
public interface UsuarioValidarInformacoesService {
    UsuarioDTO validarInformacoes(UsuarioValidarDTO usuarioValidarDTO);
}
