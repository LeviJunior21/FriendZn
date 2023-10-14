package com.codelephant.friendzone.exception;

public class FriendZoneException extends  RuntimeException {
    public FriendZoneException() {
        super("Erro inesperado no Mercado Fácil!");
    }

    public FriendZoneException(String message) {
        super(message);
    }
}
