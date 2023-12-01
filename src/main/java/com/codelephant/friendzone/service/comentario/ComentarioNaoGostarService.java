package com.codelephant.friendzone.service.comentario;

import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoPostRequestDTO;

@FunctionalInterface
public interface ComentarioNaoGostarService {
    ComentarioGostarOuNaoDTO naoGostar(ComentarioGostarOuNaoPostRequestDTO comentarioGostarOuNaoPostRequestDTO, Long idPublicacao, Long idComentario);
}
