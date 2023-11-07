package com.codelephant.friendzone.service.comentario;

import com.codelephant.friendzone.dto.comentario.ComentarioDTO;

import java.util.List;

@FunctionalInterface
public interface ComentarioListarService {
    List<ComentarioDTO> listar(Long idPublicacao);
}
