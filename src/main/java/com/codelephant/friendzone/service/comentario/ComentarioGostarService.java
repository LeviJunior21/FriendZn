package com.codelephant.friendzone.service.comentario;

import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoPostRequestDTO;

@FunctionalInterface
public interface ComentarioGostarService {
    ComentarioGostarOuNaoDTO gostar(ComentarioGostarOuNaoPostRequestDTO comentarioGostarPostRequestDTO, Long idPublicacao, Long idComentario);
}
