package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.dto.usuario.UsuarioDTO;
import com.codelephant.friendzone.dto.usuario.UsuarioValidarDTO;
import com.codelephant.friendzone.exception.usuario.CodigoDeAcessoDiferenteException;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.UsuarioRepository;
import com.codelephant.friendzone.utils.validacao.ValidarCodigoAcesso;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioValidarInformacoesPadraoService implements UsuarioValidarInformacoesService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ValidarCodigoAcesso validarCodigoAcesso;

    @Override
    public UsuarioDTO validarInformacoes(UsuarioValidarDTO usuarioValidarDTO) {
        Usuario usuario = usuarioRepository.findByEmail(usuarioValidarDTO.getEmail()).orElseThrow(UsuarioNaoExisteException::new);
        validarCodigoAcesso.validar(usuarioValidarDTO.getCodigoAcesso(), usuario.getCodigoAcesso()).orElseThrow(CodigoDeAcessoDiferenteException::new);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
}
