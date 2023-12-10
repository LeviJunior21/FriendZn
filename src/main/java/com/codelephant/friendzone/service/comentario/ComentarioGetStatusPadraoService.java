package com.codelephant.friendzone.service.comentario;

import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoDTO;
import com.codelephant.friendzone.dto.usuario.UsuarioDTO;
import com.codelephant.friendzone.exception.comentario.ComentarioNaoExisteException;
import com.codelephant.friendzone.exception.publicacao.PublicacaoNaoExisteException;
import com.codelephant.friendzone.model.Comentario;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioGetStatusPadraoService implements ComentarioGetStatusService {
    @Autowired
    PublicacaoRepository publicacaoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ComentarioGostarOuNaoDTO get(Long idPublicacao, Long idComentario) {
        Publicacao publicacao = publicacaoRepository.findById(idPublicacao).orElseThrow(PublicacaoNaoExisteException::new);
        Comentario comentarioBuscado = publicacao.getComentarios().stream().filter(comentario -> comentario.getId().equals(idComentario)).findFirst().orElseThrow(ComentarioNaoExisteException::new);

        List<UsuarioDTO> usuariosGostaram = comentarioBuscado.getGostaram().stream().map(usuario1 -> modelMapper.map(usuario1, UsuarioDTO.class)).collect(Collectors.toList());
        List<UsuarioDTO> usuariosNaoGostaram = comentarioBuscado.getNaoGostaram().stream().map(usuario1 -> modelMapper.map(usuario1, UsuarioDTO.class)).collect(Collectors.toList());

        ComentarioGostarOuNaoDTO comentarioGostarOuNaoDTO = ComentarioGostarOuNaoDTO.builder()
                .gostou(usuariosGostaram)
                .naoGostou(usuariosNaoGostaram)
                .build();

        return comentarioGostarOuNaoDTO;
    }
}
