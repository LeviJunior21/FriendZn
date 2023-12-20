package com.codelephant.friendzone.service.publicacao;

import com.codelephant.friendzone.dto.publicacao.PublicacaoDTO;
import com.codelephant.friendzone.dto.publicacao.PublicacaoPostPutRequestDTO;
import com.codelephant.friendzone.exception.usuario.CodigoDeAcessoDiferenteException;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.PublicacaoRepository;
import com.codelephant.friendzone.repository.UsuarioRepository;
import com.codelephant.friendzone.utils.validacao.ValidarCodigoAcesso;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PublicacaoCriarPadraoService implements PublicacaoCriarService {
    @Autowired
    PublicacaoRepository publicacaoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ValidarCodigoAcesso validarCodigoAcesso;

    @Override
    public PublicacaoDTO salvar(PublicacaoPostPutRequestDTO publicacaoPostPutRequestDTO, Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(UsuarioNaoExisteException::new);
        validarCodigoAcesso.validar(publicacaoPostPutRequestDTO.getCodigoAcesso(), usuario.getCodigoAcesso()).orElseThrow(CodigoDeAcessoDiferenteException::new);

        Publicacao publicacao = modelMapper.map(publicacaoPostPutRequestDTO, Publicacao.class);
        publicacao.getInteressados().add(usuario);
        publicacao.setUsuario(usuario);
        publicacao = publicacaoRepository.save(publicacao);
        usuario.getPublicacoes().add(publicacao);
        usuario.getPublicacoesSeguidas().add(publicacao);
        usuarioRepository.save(usuario);

        PublicacaoDTO publicacaoDTO = modelMapper.map(publicacao, PublicacaoDTO.class);
        return publicacaoDTO;
    }
}
