package com.codelephant.friendzone.service.comentario;

import com.codelephant.friendzone.dto.comentario.ComentarioDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioPostPutRequestDTO;

import java.util.List;

@FunctionalInterface
public interface ComentarioCriarService {
    List<ComentarioDTO> salvar(ComentarioPostPutRequestDTO comentarioPostPutRequestDTO);
}
