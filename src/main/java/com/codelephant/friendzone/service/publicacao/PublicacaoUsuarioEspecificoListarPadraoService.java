package com.codelephant.friendzone.service.publicacao;

import com.codelephant.friendzone.dto.publicacao.PublicacaoDTO;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacaoUsuarioEspecificoListarPadraoService implements PublicacaoUsuarioEspecificoListarService {

    @Autowired
    PublicacaoRepository publicacaoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<PublicacaoDTO> listar(Long id) {
        List<Publicacao> publicacoes = publicacaoRepository.findByIdUser(id);
        List<PublicacaoDTO> publicacaoDTOS = publicacoes.stream().map(publicacao -> modelMapper.map(publicacao, PublicacaoDTO.class)).collect(Collectors.toList());
        return publicacaoDTOS;
    }
}
