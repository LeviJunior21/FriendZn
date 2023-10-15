package com.codelephant.friendzone.exception.comentario;

import com.codelephant.friendzone.exception.FriendZoneException;

public class ComentarioNaoExisteException extends FriendZoneException {
    public ComentarioNaoExisteException() {
        super("A publicação com esse id nao existe.");
    }
}
