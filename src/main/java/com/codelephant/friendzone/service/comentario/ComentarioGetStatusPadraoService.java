package com.codelephant.friendzone.service.comentario;

import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoDTO;
import com.codelephant.friendzone.exception.comentario.ComentarioNaoExisteException;
import com.codelephant.friendzone.exception.publicacao.PublicacaoNaoExisteException;
import com.codelephant.friendzone.model.Comentario;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioGetStatusPadraoService implements ComentarioGetStatusService {
    @Autowired
    PublicacaoRepository publicacaoRepository;

    @Override
    public ComentarioGostarOuNaoDTO get(Long idPublicacao, Long idComentario) {
        Publicacao publicacao = publicacaoRepository.findById(idPublicacao).orElseThrow(PublicacaoNaoExisteException::new);
        Comentario comentarioEncontrado = publicacao.getComentarios().stream()
                .filter(comentario -> comentario.getId().equals(idComentario)).findFirst().orElseThrow(ComentarioNaoExisteException::new);

        ComentarioGostarOuNaoDTO comentarioGostarOuNaoDTO = ComentarioGostarOuNaoDTO.builder()
                .gostou(comentarioEncontrado.getGostaram().size())
                .naoGostou(comentarioEncontrado.getNaoGostaram().size())
                .build();
        return comentarioGostarOuNaoDTO;
    }
}
