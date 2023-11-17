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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ComentarioDTO> salvar(ComentarioPostPutRequestDTO comentarioPostPutRequestDTO) {
        Usuario usuario = usuarioRepository.findById(comentarioPostPutRequestDTO.getIdUsuario()).orElseThrow(UsuarioNaoExisteException::new);
        if (!comentarioPostPutRequestDTO.getCodigoAcesso().equals(usuario.getCodigoAcesso())) {
            throw new CodigoDeAcessoDiferenteException();
        }
        Publicacao publicacao = publicacaoRepository.findById(comentarioPostPutRequestDTO.getIdPublicacao()).orElseThrow(PublicacaoNaoExisteException::new);
        Comentario comentario = modelMapper.map(comentarioPostPutRequestDTO, Comentario.class);
        comentario.setUsuario(usuario);
        comentario.setPublicacao(publicacao);
        comentario.setGostaram(new HashSet<Usuario>());
        comentario.setNaoGostaram(new HashSet<Usuario>());
        publicacao.getComentarios().add(comentario);
        publicacaoRepository.save(publicacao);

        List<Comentario> comentarios = publicacaoRepository.findById(comentarioPostPutRequestDTO.getIdUsuario()).get().getComentarios();
        return comentarios.stream()
                .map(comentarioI -> modelMapper.map(comentarioI, ComentarioDTO.class))
                .collect(Collectors.toList());
    }
}
