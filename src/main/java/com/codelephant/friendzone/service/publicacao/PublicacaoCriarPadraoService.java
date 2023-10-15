package com.codelephant.friendzone.service.publicacao;

import com.codelephant.friendzone.dto.publicacao.PublicacaoPostPutRequestDTO;
import com.codelephant.friendzone.exception.usuario.CodigoDeAcessoDiferenteException;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import com.codelephant.friendzone.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Transactional
public class PublicacaoCriarPadraoService implements PublicacaoCriarService {
    @Autowired
    PublicacaoRepository publicacaoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public void salvar(PublicacaoPostPutRequestDTO publicacaoPostPutRequestDTO, Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(UsuarioNaoExisteException::new);
        if (!publicacaoPostPutRequestDTO.getCodigoAcesso().equals(usuario.getCodigoAcesso())) {
            throw new CodigoDeAcessoDiferenteException();
        }
        Publicacao publicacao = modelMapper.map(publicacaoPostPutRequestDTO, Publicacao.class);
        publicacao.setComentarios(new ArrayList<>());
        usuario.getPublicacoes().add(publicacao);
        usuarioRepository.save(usuario);
    }
}
