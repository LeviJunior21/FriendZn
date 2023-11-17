package com.codelephant.friendzone.utils;

import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class WebSocketTests {
    private WebSocketStompClient webSocketStompClient = new WebSocketStompClient(new StandardWebSocketClient());
    private StompSession stompSession;
    private String webSocketUrl;
    private static final Integer timeOut = 5;

    public WebSocketTests(String websocketUrl) {
        this.webSocketUrl = websocketUrl;
    }

    public String subscribeAndGet(AtomicReference<String> messageReceive, String subscribe) throws Exception {
        CompletableFuture<StompSession> sessionFuture = new CompletableFuture<>();

        webSocketStompClient.connect(this.webSocketUrl, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe(subscribe, new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return byte[].class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        messageReceive.set(new String((byte[]) payload));
                    }
                });

                sessionFuture.complete(session);
            }
        });

        stompSession = sessionFuture.get(this.timeOut, TimeUnit.SECONDS);

        Thread.sleep((this.timeOut + 1) * 1000);
        return messageReceive.get();
    }

    public void sendAfterSubscribe(String destination, String objectAsString) {
        byte[] payload = objectAsString.getBytes(StandardCharsets.UTF_8);
        stompSession.send(destination, payload);
    }


    public void justSend(String destination, String objectAsString) throws Exception {
        StompSession session = webSocketStompClient.connect(
                webSocketUrl, new StompSessionHandlerAdapter() {}
        ).get(5, TimeUnit.SECONDS);
        session.send(destination, objectAsString);
    }


}
