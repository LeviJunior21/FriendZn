package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.LoginType;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioValidarPadraoService implements UsuarioValidarService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public boolean validarIdAuth(Long idGithub, LoginType loginType) {
        boolean result = false;
        try {
            Usuario usuario = usuarioRepository.findByIdAuth(idGithub, loginType).orElseThrow(UsuarioNaoExisteException::new);
            if (usuario.getCodigoAcesso().equals(idGithub)) {
                result = true;
            }
        } catch (Exception e) {}
        return result;
    }

}
