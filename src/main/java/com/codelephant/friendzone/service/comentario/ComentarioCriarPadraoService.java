package com.codelephant.friendzone.service.comentario;

import com.codelephant.friendzone.dto.comentario.ComentarioDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioPostPutRequestDTO;
import com.codelephant.friendzone.exception.publicacao.PublicacaoNaoExisteException;
import com.codelephant.friendzone.exception.usuario.CodigoDeAcessoDiferenteException;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Comentario;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import com.codelephant.friendzone.repository.UsuarioRepository;
import com.codelephant.friendzone.utils.validacao.ValidarCodigoAcesso;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ComentarioCriarPadraoService implements ComentarioCriarService {
    @Autowired
    PublicacaoRepository publicacaoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ValidarCodigoAcesso validarCodigoAcesso;

    @Override
    public ComentarioDTO salvar(ComentarioPostPutRequestDTO comentarioPostPutRequestDTO, Long idPublicacao) {
        Usuario usuario = usuarioRepository.findById(comentarioPostPutRequestDTO.getIdUsuario()).orElseThrow(UsuarioNaoExisteException::new);
        Publicacao publicacao = publicacaoRepository.findById(idPublicacao).orElseThrow(PublicacaoNaoExisteException::new);
        validarCodigoAcesso.validar(comentarioPostPutRequestDTO.getCodigoAcesso(), usuario.getCodigoAcesso()).orElseThrow(CodigoDeAcessoDiferenteException::new);

        Comentario comentario = buildComentario(comentarioPostPutRequestDTO, usuario, publicacao);
        publicacao.getComentarios().add(comentario);
        publicacao.getInteressados().add(usuario);
        publicacaoRepository.save(publicacao);

        usuario.getPublicacoesSeguidas().add(publicacao);
        usuarioRepository.save(usuario);

        return ComentarioDTO.builder()
                .id(comentario.getId())
                .comentario(comentarioPostPutRequestDTO.getComentario())
                .usuarioId(comentario.getUsuario().getId())
                .timestamp(comentarioPostPutRequestDTO.getTimestamp())
                .build();
    }

    private Comentario buildComentario(ComentarioPostPutRequestDTO comentarioPostPutRequestDTO, Usuario usuario, Publicacao publicacao) {
        Comentario comentario = Comentario.builder().build();
        comentario.setUsuario(usuario);
        comentario.setPublicacao(publicacao);
        comentario.setComentario(comentarioPostPutRequestDTO.getComentario());
        comentario.setCodigoAcesso(comentarioPostPutRequestDTO.getCodigoAcesso());
        comentario.setTimestamp(comentarioPostPutRequestDTO.getTimestamp());
        return comentario;
    }
}
