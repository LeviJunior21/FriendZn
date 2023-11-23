package com.codelephant.friendzone.service.chat;

import com.codelephant.friendzone.dto.chat.ChatPostPutRequestDTO;
import com.codelephant.friendzone.exception.usuario.UsuarioNaoExisteException;
import com.codelephant.friendzone.model.Conversa;
import com.codelephant.friendzone.model.Mensagem;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.repository.ConversaRepository;
import com.codelephant.friendzone.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public Mensagem salvar(ChatPostPutRequestDTO mensagemPostPutRequestDTO) {
        Usuario usuario1 = usuarioRepository.findById(mensagemPostPutRequestDTO.getRemetente()).orElseThrow(UsuarioNaoExisteException::new);
        usuarioRepository.findById(mensagemPostPutRequestDTO.getReceptor()).orElseThrow(UsuarioNaoExisteException::new);

        List<Conversa> conversas = conversaRepository.findByUserio1Id(usuario1.getId());
        Conversa conversa = binarySearch(conversas, usuario1.getId(), 0, conversas.size() - 1);
        Mensagem mensagem = modelMapper.map(mensagemPostPutRequestDTO, Mensagem.class);

        if (conversa != null) {
            conversa.getMensagens().add(mensagem);
            conversaRepository.save(conversa);
            return mensagem;
        } else {
            Conversa novaConversa = Conversa.builder()
                    .idUsuario1(mensagemPostPutRequestDTO.getRemetente())
                    .idUsuario2(mensagemPostPutRequestDTO.getReceptor())
                    .mensagens(new ArrayList<>())
                    .build();
            mensagem.setConversa(conversa);
            novaConversa.getMensagens().add(mensagem);
            conversaRepository.save(novaConversa);
            return mensagem;
        }
    }

    private Conversa binarySearch(List<Conversa> conversas, Long idUsuario, int left, int right) {
        if (left > right) {
            return null;
        }
        int meio = (left + right) / 2;
        Conversa conversa = conversas.get(meio);

        if (conversa.getIdUsuario1().equals(idUsuario)) {
            return conversa;
        }

        if (idUsuario < conversa.getIdUsuario1()) {
            return binarySearch(conversas, idUsuario, left, meio - 1);
        }
        else {
            return binarySearch(conversas, idUsuario, meio + 1, right);
        }
    }
}
