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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioCriarPadraoService implements ComentarioCriarService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PublicacaoRepository publicacaoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public ComentarioDTO salvar(ComentarioPostPutRequestDTO comentarioPostPutRequestDTO, Long idPublicacao, Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(UsuarioNaoExisteException::new);
        if (!comentarioPostPutRequestDTO.getCodigoAcesso().equals(usuario.getCodigoAcesso())) {
            throw new CodigoDeAcessoDiferenteException();
        }
        Publicacao publicacao = publicacaoRepository.findById(idPublicacao).orElseThrow(PublicacaoNaoExisteException::new);
        Comentario comentario = modelMapper.map(comentarioPostPutRequestDTO, Comentario.class);
        comentario.setUsuario(usuario);
        comentario.setPublicacao(publicacao);
        publicacao.getComentarios().add(comentario);
        publicacaoRepository.save(publicacao);
        ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);
        return comentarioDTO;
    }
}
