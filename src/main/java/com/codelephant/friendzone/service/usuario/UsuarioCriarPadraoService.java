package com.codelephant.friendzone.service.usuario;
import com.codelephant.friendzone.dto.usuario.UsuarioDTO;
import com.codelephant.friendzone.dto.usuario.UsuarioPostPutRequestDTO;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioCriarPadraoService implements UsuarioCriarService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public UsuarioDTO salvar(UsuarioPostPutRequestDTO usuarioPostPutRequestDTO) {
        Usuario usuario = modelMapper.map(usuarioPostPutRequestDTO, Usuario.class);
        Usuario usuarioSaved = usuarioRepository.save(usuario);
        UsuarioDTO usuarioDTO = modelMapper.map(usuarioSaved, UsuarioDTO.class);
        return usuarioDTO;
    }
}
