package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.exception.usuario.EmailUsuarioNaoExisteException;
import com.codelephant.friendzone.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioExistenciaPadraoService implements UsuarioExistenciaService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Boolean verificar(String email) {
        usuarioRepository.findByEmail(email).orElseThrow(EmailUsuarioNaoExisteException::new);
        return true;
    }
}
