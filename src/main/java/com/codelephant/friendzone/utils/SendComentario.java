package com.codelephant.friendzone.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.stomp.StompSession;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendComentario implements Runnable {
    private StompSession stompSession;
    private String destination;
    String data;

    @Override
    public void run() {
        stompSession.send(destination, data);
    }
}
