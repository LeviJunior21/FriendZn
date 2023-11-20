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
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@Transactional
public class ComentarioCriarPadraoService implements ComentarioCriarService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PublicacaoRepository publicacaoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public ComentarioDTO salvar(ComentarioPostPutRequestDTO comentarioPostPutRequestDTO, Long idPublicacao) {
        Usuario usuario = usuarioRepository.findById(comentarioPostPutRequestDTO.getIdUsuario())
                .orElseThrow(UsuarioNaoExisteException::new);

        Publicacao publicacao = publicacaoRepository.findById(idPublicacao)
                .orElseThrow(PublicacaoNaoExisteException::new);

        if (!comentarioPostPutRequestDTO.getCodigoAcesso().equals(usuario.getCodigoAcesso())) {
            throw new CodigoDeAcessoDiferenteException();
        }

        Comentario comentario = buildComentario(comentarioPostPutRequestDTO, usuario, publicacao);

        Hibernate.initialize(comentario.getGostaram());
        Hibernate.initialize(comentario.getNaoGostaram());

        publicacao.getComentarios().add(comentario);
        publicacaoRepository.save(publicacao);

        return ComentarioDTO.builder()
                .id(comentario.getId())
                .comentario(comentarioPostPutRequestDTO.getComentario())
                .usuarioId(comentario.getUsuario().getId())
                .gostaram(new HashSet<>())
                .naoGostaram(new HashSet<>())
                .timestamp(comentarioPostPutRequestDTO.getTimestamp())
                .build();
    }

    private Comentario buildComentario(ComentarioPostPutRequestDTO comentarioPostPutRequestDTO, Usuario usuario, Publicacao publicacao) {
        Comentario comentario = Comentario.builder().build();
        comentario.setUsuario(usuario);
        comentario.setPublicacao(publicacao);
        comentario.setGostaram(new HashSet<Usuario>());
        comentario.setNaoGostaram(new HashSet<Usuario>());
        comentario.setComentario(comentarioPostPutRequestDTO.getComentario());
        comentario.setCodigoAcesso(comentarioPostPutRequestDTO.getCodigoAcesso());
        comentario.setTimestamp(comentarioPostPutRequestDTO.getTimestamp());
        return comentario;
    }
}
