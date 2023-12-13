package com.codelephant.friendzone.service.publicacao;

import com.codelephant.friendzone.dto.publicacao.PublicacaoDTO;

import java.util.List;

@FunctionalInterface
public interface PublicacaoUsuarioEspecificoListarService {
    List<PublicacaoDTO> listar(Long id);
}
