package com.codelephant.friendzone.service.publicacao;

import com.codelephant.friendzone.dto.publicacao.PublicacaoDTO;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import com.codelephant.friendzone.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PublicacaoListarSeguindoPadraoService implements PublicacaoListarSeguindoService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<PublicacaoDTO> listar(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(UsuarioNaoExisteException::new);
        return usuario.getPublicacoesSeguidas().stream().map(publicacao -> modelMapper.map(publicacao, PublicacaoDTO.class)).collect(Collectors.toList());
    }
}
