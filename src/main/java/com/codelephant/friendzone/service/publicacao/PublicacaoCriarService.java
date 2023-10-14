package com.codelephant.friendzone.service.publicacao;

import com.codelephant.friendzone.dto.publicacao.PublicacaoPostPutRequestDTO;

@FunctionalInterface
public interface PublicacaoCriarService {
    void salvar(PublicacaoPostPutRequestDTO publicacaoPostPutRequestDTO, Long idUsuario);
}
