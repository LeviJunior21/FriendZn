package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioDeletarPadraoService implements UsuarioDeletarService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public void deletar(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }
}
