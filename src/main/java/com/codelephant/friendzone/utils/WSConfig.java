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
public class WSConfig {
    private WebSocketStompClient webSocketStompClient;
    private final Semaphore barrier = new Semaphore(0);
    private String destination;
    private String subscribe;
    private String data;
    private String ws;

    public void run(AtomicReference<String> messageReceive) throws Exception {
        WSReceive wsReceive = WSReceive.builder()
                .webSocketUrl(ws)
                .webSocketStompClient(webSocketStompClient)
                .messageReceive(messageReceive)
                .subscribe(subscribe)
                .semaphore(barrier)
                .build();

        Thread threadReceiver = new Thread(wsReceive);
        threadReceiver.start();

        barrier.acquire();

        WSSend wsSend = WSSend.builder()
                .data(data)
                .destination(destination)
                .stompSession(wsReceive.getStompSession())
                .build();
        Thread threadSender = new Thread(wsSend);
        threadSender.start();

        Thread.sleep(wsReceive.getTimeOutAndIncrement(100));
    }

}
