package com.codelephant.friendzone.exception.usuario;

import com.codelephant.friendzone.exception.FriendZoneException;

public class EmailUsuarioNaoExisteException extends FriendZoneException {
    public EmailUsuarioNaoExisteException() {
        super("O usuario com esse id nao existe.");
    }
}
