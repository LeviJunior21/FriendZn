package com.codelephant.friendzone.exception.usuario;

import com.codelephant.friendzone.exception.FriendZoneException;

public class UsuarioNaoExisteException extends FriendZoneException {
    public UsuarioNaoExisteException() {
        super("O usuario com esse id nao existe.");
    }
}
