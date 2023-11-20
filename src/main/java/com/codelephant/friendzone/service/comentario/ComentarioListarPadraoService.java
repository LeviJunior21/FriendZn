package com.codelephant.friendzone.service.comentario;

import com.codelephant.friendzone.dto.comentario.ComentarioDTO;
import com.codelephant.friendzone.exception.publicacao.PublicacaoNaoExisteException;
import com.codelephant.friendzone.model.Comentario;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.repository.ComentarioRepository;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioListarPadraoService implements ComentarioListarService {

    @Autowired
    PublicacaoRepository publicacaoRepository;
    @Autowired
    ComentarioRepository comentarioRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ComentarioDTO> listar(Long id) {
        if (id != null && id > 0) {
            Publicacao publicacao = publicacaoRepository.findById(id).orElseThrow(PublicacaoNaoExisteException::new);
            List<Comentario> comentarios = new ArrayList<>(publicacao.getComentarios());
            return comentarios.stream().map(comentario -> modelMapper.map(comentario, ComentarioDTO.class)).collect(Collectors.toList());
        }
        return comentarioRepository.findAll().stream().map(comentario -> modelMapper.map(comentario, ComentarioDTO.class)).collect((Collectors.toList()));
    }
}
