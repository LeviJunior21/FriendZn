package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioExistenciaPadraoSerivce implements UsuarioExistenciaService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Boolean verificar(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        Boolean response = false;
        if (usuario != null) {
            response = true;
        }
        return response;
    }
}
