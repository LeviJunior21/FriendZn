package com.codelephant.friendzone.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioWSConfig {
    private WebSocketStompClient webSocketStompClient;
    private final Semaphore barrier = new Semaphore(0);
    private String destination;
    private String subscribe;
    private String data;
    private String ws;

    public void run(AtomicReference<String> messageReceive) throws Exception {
        ReceiveComentario threadReceiveComentario = ReceiveComentario.builder()
                .webSocketUrl(ws)
                .webSocketStompClient(webSocketStompClient)
                .messageReceive(messageReceive)
                .subscribe(subscribe)
                .semaphore(barrier)
                .build();

        Thread threadReceive = new Thread(threadReceiveComentario);
        threadReceive.start();

        barrier.acquire();

        SendComentario threadSendComentario = SendComentario.builder()
                .data(data)
                .destination(destination)
                .stompSession(threadReceiveComentario.getStompSession())
                .build();
        Thread threadSend = new Thread(threadSendComentario);
        threadSend.start();

        Thread.sleep(threadReceiveComentario.getTimeOutAndIncrement(1000));
    }

}
