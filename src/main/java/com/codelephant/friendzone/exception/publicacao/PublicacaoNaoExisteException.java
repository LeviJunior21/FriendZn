package com.codelephant.friendzone.exception.publicacao;

import com.codelephant.friendzone.exception.FriendZoneException;

public class PublicacaoNaoExisteException extends FriendZoneException {
    public PublicacaoNaoExisteException() {
     super("A publicaacao com esse id nao existe.");
    }
}
