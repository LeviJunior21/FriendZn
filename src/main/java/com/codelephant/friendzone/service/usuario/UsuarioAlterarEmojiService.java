package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.dto.usuario.UsuarioEmojiPatchRequestDTO;

@FunctionalInterface
public interface UsuarioAlterarEmojiService {
    String alterar(UsuarioEmojiPatchRequestDTO usuarioEmojiPatchRequestDTO, Long id);
}
