package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.LoginType;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioGetByIdGitHubPadraoService implements UsuarioGetByIdGitHubService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Long getIDServer(Long idAuth) {
        Usuario usuario = usuarioRepository.findByIdAuth(idAuth, LoginType.GitHub).orElseThrow(UsuarioNaoExisteException::new);
        return usuario.getId();
    }
}
