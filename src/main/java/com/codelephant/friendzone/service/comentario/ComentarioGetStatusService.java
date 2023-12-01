package com.codelephant.friendzone.service.comentario;

import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoDTO;

@FunctionalInterface
public interface ComentarioGetStatusService {
    public ComentarioGostarOuNaoDTO get(Long idPublicacao, Long idComentario);
}
