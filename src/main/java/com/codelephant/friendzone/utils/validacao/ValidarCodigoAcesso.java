package com.codelephant.friendzone.utils.validacao;

import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ValidarCodigoAcesso {
    public Optional<Boolean> validar(Long codigoRecebido, Long codigoBanco) {
        if (codigoRecebido != null && codigoBanco != null && codigoRecebido.equals(codigoBanco)) {
            return Optional.of(true);
        }
        return Optional.empty();
    }
}
