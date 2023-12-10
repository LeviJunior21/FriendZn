package com.codelephant.friendzone.service.comentario;

import com.codelephant.friendzone.dto.comentario.ComentarioDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoDTO;
import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoPostRequestDTO;
import com.codelephant.friendzone.dto.usuario.UsuarioDTO;
import com.codelephant.friendzone.exception.comentario.ComentarioNaoExisteException;
import com.codelephant.friendzone.exception.publicacao.PublicacaoNaoExisteException;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Comentario;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import com.codelephant.friendzone.repository.UsuarioRepository;
import com.codelephant.friendzone.utils.validacao.ValidarCodigoAcesso;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ComentarioGostarPadraoService implements ComentarioGostarService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PublicacaoRepository publicacaoRepository;
    @Autowired
    ValidarCodigoAcesso validarCodigoAcesso;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ComentarioGostarOuNaoDTO gostar(ComentarioGostarOuNaoPostRequestDTO comentarioGostarOuNaoPostRequestDTO, Long idPublicacao, Long idComentario) {
        Publicacao publicacao = publicacaoRepository.findById(idPublicacao).orElseThrow(PublicacaoNaoExisteException::new);
        Usuario usuario = usuarioRepository.findById(comentarioGostarOuNaoPostRequestDTO.getIdUsuario()).orElseThrow(UsuarioNaoExisteException::new);
        validarCodigoAcesso.validar(comentarioGostarOuNaoPostRequestDTO.getCodigoAcesso(), usuario.getCodigoAcesso());

        Comentario comentarioBuscado = publicacao.getComentarios().stream().filter(comentario -> comentario.getId().equals(idComentario)).findFirst().orElseThrow(ComentarioNaoExisteException::new);

        if (!comentarioBuscado.getGostaram().contains(usuario)) { comentarioBuscado.getGostaram().add(usuario); }
        if (comentarioBuscado.getNaoGostaram().contains(usuario)) { comentarioBuscado.getNaoGostaram().remove(usuario); }

        publicacaoRepository.save(publicacao);

        List<UsuarioDTO> usuariosGostaram = comentarioBuscado.getGostaram().stream().map(usuario1 -> modelMapper.map(usuario1, UsuarioDTO.class)).collect(Collectors.toList());
        List<UsuarioDTO> usuariosNaoGostaram = comentarioBuscado.getNaoGostaram().stream().map(usuario1 -> modelMapper.map(usuario1, UsuarioDTO.class)).collect(Collectors.toList());

        ComentarioGostarOuNaoDTO comentarioGostarOuNaoDTO = ComentarioGostarOuNaoDTO.builder()
                .gostou(usuariosGostaram)
                .naoGostou(usuariosNaoGostaram)
                .build();

        return comentarioGostarOuNaoDTO;
    }
}
