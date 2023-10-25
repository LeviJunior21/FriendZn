package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.dto.usuario.UsuarioDTO;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioListarPadraoService implements UsuarioListarService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<UsuarioDTO> listar(Long id) {
        if (id != null && id > 0) {
            Usuario usuario = usuarioRepository.findById(id).orElseThrow(UsuarioNaoExisteException::new);
            return Collections.singletonList(modelMapper.map(usuario, UsuarioDTO.class));
        }
        return usuarioRepository.findAll().stream().map(usuario -> modelMapper.map(usuario, UsuarioDTO.class)).collect((Collectors.toList()));
    }
}
