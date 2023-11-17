package com.codelephant.friendzone.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveComentario implements Runnable {
    private WebSocketStompClient webSocketStompClient;
    private AtomicReference<String> messageReceive;
    private static final Integer timeOut = 5;
    private StompSession stompSession;
    private String webSocketUrl;
    private Semaphore semaphore;
    private String subscribe;

    @Override
    public void run() {
        try {
            stompSession = webSocketStompClient.connect(this.webSocketUrl, new StompSessionHandlerAdapter() {
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
                    semaphore.release();
                }
            }).get(this.timeOut, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getTimeOutAndIncrement(Integer value) {
        return this.timeOut * 1000 + value;
    }
}
