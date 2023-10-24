package com.codelephant.friendzone.service.publicacao;

import com.codelephant.friendzone.dto.publicacao.PublicacaoDTO;
import com.codelephant.friendzone.dto.publicacao.PublicacaoPostPutRequestDTO;

@FunctionalInterface
public interface PublicacaoCriarService {
    PublicacaoDTO salvar(PublicacaoPostPutRequestDTO publicacaoPostPutRequestDTO, Long idUsuario);
}
