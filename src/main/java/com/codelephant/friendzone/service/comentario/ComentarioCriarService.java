package com.codelephant.friendzone.service.comentario;

import com.codelephant.friendzone.dto.comentario.ComentarioDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioPostPutRequestDTO;

@FunctionalInterface
public interface ComentarioCriarService {
    ComentarioDTO salvar(ComentarioPostPutRequestDTO comentarioPostPutRequestDTO, Long idPublicacao, Long idUsuario);
}
