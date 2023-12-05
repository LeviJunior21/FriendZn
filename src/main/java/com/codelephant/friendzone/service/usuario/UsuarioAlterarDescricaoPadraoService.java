package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.dto.usuario.UsuarioDescricaoPutRequestDTO;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.UsuarioRepository;
import com.codelephant.friendzone.utils.validacao.ValidarCodigoAcesso;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioAlterarDescricaoPadraoService implements UsuarioAlterarDescricaoService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ValidarCodigoAcesso validarCodigoAcesso;

    @Override
    public void alterarDescricao(UsuarioDescricaoPutRequestDTO usuarioDescricaoPutRequestDTO, Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(UsuarioNaoExisteException::new);
        validarCodigoAcesso.validar(usuario.getCodigoAcesso(), usuarioDescricaoPutRequestDTO.getCodigoAcesso());
        usuario.setDescricao(usuarioDescricaoPutRequestDTO.getDescricao());
        usuarioRepository.save(usuario);
    }
}
