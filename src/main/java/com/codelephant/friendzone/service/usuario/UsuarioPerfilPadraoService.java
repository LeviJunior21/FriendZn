package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.dto.usuario.UsuarioPerfilDTO;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioPerfilPadraoService implements UsuarioPerfilService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public UsuarioPerfilDTO buscar(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(UsuarioNaoExisteException::new);
        UsuarioPerfilDTO usuarioPerfilDTO = modelMapper.map(usuario, UsuarioPerfilDTO.class);
        return usuarioPerfilDTO;
    }
}
