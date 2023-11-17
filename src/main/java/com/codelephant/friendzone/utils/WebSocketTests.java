package com.codelephant.friendzone.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class WebSocketTests<T> {
    @Autowired
    private WebSocketStompClient webSocketStompClient;
    @Autowired
    private StompSession stompSession;
    private String webSocketUrl;
    private static final Integer timeOut = 5;

    public WebSocketTests(String websocketUrl) {
        this.webSocketUrl = websocketUrl;
    }

    public String subscribeAndGet(AtomicReference<String> messageReceive, String subscribe) throws Exception {
        stompSession = webSocketStompClient.connect(webSocketUrl, new StompSessionHandlerAdapter() {
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
            }
        }).get(this.timeOut, TimeUnit.SECONDS);

        Thread.sleep((this.timeOut + 1)  * 1000);
        return messageReceive.get();
    }

    public void sendAfterSubscribe(String destination, String objectAsString) {
        stompSession.send(destination, objectAsString);
    }

    public void justSend(String destination, String objectAsString) throws Exception {
        stompSession = webSocketStompClient.connect(webSocketUrl, new StompSessionHandlerAdapter() {}).get(5, TimeUnit.SECONDS);
        stompSession.send(destination, objectAsString);
    }

}
