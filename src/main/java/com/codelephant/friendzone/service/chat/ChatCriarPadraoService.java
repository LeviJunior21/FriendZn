package com.codelephant.friendzone.service.chat;

import com.codelephant.friendzone.dto.chat.ChatPostPutRequestDTO;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Conversa;
import com.codelephant.friendzone.model.Mensagem;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.ConversaRepository;
import com.codelephant.friendzone.repository.UsuarioRepository;
import com.codelephant.friendzone.utils.busca.BuscarDado;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ChatCriarPadraoService implements ChatCriarService {
    @Autowired
    ConversaRepository conversaRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    BuscarDado buscarDado;

    @Override
    public ChatPostPutRequestDTO salvar(ChatPostPutRequestDTO chatPostPutRequestDTO) {
        Usuario usuarioRemetente = usuarioRepository.findById(chatPostPutRequestDTO.getRemetente()).orElseThrow(UsuarioNaoExisteException::new);
        Usuario usuariooReceptor = usuarioRepository.findById(chatPostPutRequestDTO.getReceptor()).orElseThrow(UsuarioNaoExisteException::new);
        Mensagem mensagem = modelMapper.map(chatPostPutRequestDTO, Mensagem.class);
        List<Conversa> conversasDoReceptor = conversaRepository.findByReceptor(usuariooReceptor.getId());

        // Busca as conversas do remetente nas conversas de quem vai receber a conversa.
        Conversa conversaDoRemetente = buscarDado.buscarConversa(conversasDoReceptor, usuarioRemetente.getId(), 0, conversasDoReceptor.size() - 1);

        if (conversaDoRemetente != null) {
            conversaDoRemetente.getMensagens().add(mensagem);
            conversaRepository.save(conversaDoRemetente);
        }

        Conversa conversa = modelMapper.map(chatPostPutRequestDTO, Conversa.class);
        mensagem.setConversa(conversa);
        conversa.getMensagens().add(mensagem);
        conversaRepository.save(conversa);
        return chatPostPutRequestDTO;
    }
}
