package com.codelephant.friendzone.service.publicacao;

import com.codelephant.friendzone.dto.publicacao.PublicacaoDTO;
import java.util.List;

@FunctionalInterface
public interface PublicacaoListarSeguindoService {
    List<PublicacaoDTO> listar(Long idUsuario);
}
