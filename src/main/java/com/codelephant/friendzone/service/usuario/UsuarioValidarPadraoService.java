package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.dto.usuario.UsuarioDTO;
import com.codelephant.friendzone.dto.usuario.UsuarioValidarDTO;
import com.codelephant.friendzone.exception.usuario.CodigoDeAcessoDiferenteException;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioValidarPadraoService implements UsuarioValidarService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public UsuarioDTO validarInformacoes(UsuarioValidarDTO usuarioValidarDTO) {
        Usuario usuario = usuarioRepository.findByEmail(usuarioValidarDTO.getEmail());
        if (usuario == null) {
            throw new UsuarioNaoExisteException();
        } else if (!usuario.getCodigoAcesso().equals(usuarioValidarDTO.getCodigoAcesso())) {
            throw new CodigoDeAcessoDiferenteException();
        }
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
}
