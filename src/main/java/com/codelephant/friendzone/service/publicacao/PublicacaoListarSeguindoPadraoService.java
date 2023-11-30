package com.codelephant.friendzone.service.publicacao;

import com.codelephant.friendzone.dto.publicacao.PublicacaoDTO;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import com.codelephant.friendzone.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacaoListarSeguindoPadraoService implements PublicacaoListarSeguindoService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PublicacaoRepository publicacaoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<PublicacaoDTO> listar(Long idUsuario) {
        System.out.println(publicacaoRepository.findAll().size());
        System.out.println(publicacaoRepository.findAll().stream().findFirst().get().getInteressados().size());
        List<Publicacao> publicacoesInteressadas = publicacaoRepository.findAll().stream().filter(
                publicacao -> publicacao.getInteressados() != null && publicacao.getInteressados().stream().anyMatch(
                        usuario1 -> usuario1.getId().equals(idUsuario)
                )).collect(Collectors.toList()).stream().toList();

        return publicacoesInteressadas.stream().map(
                publicacao -> modelMapper.map(publicacao, PublicacaoDTO.class))
                .collect(Collectors.toList());
    }
}
