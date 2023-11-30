package com.codelephant.friendzone.utils.busca;

import com.codelephant.friendzone.model.Conversa;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BuscarDado {

    public Conversa binarySearchConversa(List<Conversa> conversas, Long idUsuario, int left, int right) {
        if (left > right) {
            return null;
        }
        int meio = (left + right) / 2;
        Conversa conversa = conversas.get(meio);

        if (conversa.getRemetente().equals(idUsuario)) {
            return conversa;
        }

        if (idUsuario < conversa.getRemetente()) {
            return binarySearchConversa(conversas, idUsuario, left, meio - 1);
        }
        else {
            return binarySearchConversa(conversas, idUsuario, meio + 1, right);
        }
    }
}
