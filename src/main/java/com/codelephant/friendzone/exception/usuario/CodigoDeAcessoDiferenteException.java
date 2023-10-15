package com.codelephant.friendzone.exception.usuario;

import com.codelephant.friendzone.exception.FriendZoneException;

public class CodigoDeAcessoDiferenteException extends FriendZoneException {
    public CodigoDeAcessoDiferenteException() {
        super("O codigo de acesso eh diferente.");
    }
}
