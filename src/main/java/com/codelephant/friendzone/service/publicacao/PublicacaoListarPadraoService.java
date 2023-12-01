package com.codelephant.friendzone.service.publicacao;

import com.codelephant.friendzone.dto.publicacao.PublicacaoDTO;
import com.codelephant.friendzone.exception.publicacao.PublicacaoNaoExisteException;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacaoListarPadraoService implements PublicacaoListarService {
    @Autowired
    PublicacaoRepository publicacaoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<PublicacaoDTO> listar(Long id) {
        if (id != null && id > 0) {
            Publicacao publicacao = publicacaoRepository.findById(id).orElseThrow(PublicacaoNaoExisteException::new);
            return Collections.singletonList(modelMapper.map(publicacao, PublicacaoDTO.class));
        }
        return publicacaoRepository.findAll().stream().map(publicacao -> modelMapper.map(publicacao, PublicacaoDTO.class)).collect((Collectors.toList()));
    }
}
