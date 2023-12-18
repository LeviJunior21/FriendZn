package com.codelephant.friendzone.service.usuario;

import com.codelephant.friendzone.dto.usuario.UsuarioEmojiPatchRequestDTO;
import com.codelephant.friendzone.exception.usuario.CodigoDeAcessoDiferenteException;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.UsuarioRepository;
import com.codelephant.friendzone.utils.validacao.ValidarCodigoAcesso;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioAlterarEmojiPadraoService implements UsuarioAlterarEmojiService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ValidarCodigoAcesso validarCodigoAcesso;

    @Override
    public String alterar(UsuarioEmojiPatchRequestDTO usuarioEmojiPatchRequestDTO, Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(UsuarioNaoExisteException::new);
        validarCodigoAcesso.validar(usuarioEmojiPatchRequestDTO.getCodigoAcesso(), usuario.getCodigoAcesso()).orElseThrow(CodigoDeAcessoDiferenteException::new);
        usuario.setEmoji(usuarioEmojiPatchRequestDTO.getEmoji());
        usuarioRepository.save(usuario);
        return usuario.getEmoji();
    }
}
