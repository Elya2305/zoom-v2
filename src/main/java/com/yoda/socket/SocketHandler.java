package com.yoda.socket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen() && isNotSender(session, webSocketSession)) {
                webSocketSession.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("New connection!");
        sessions.add(session);
    }

    private boolean isNotSender(WebSocketSession source, WebSocketSession destination) {
        return !source.getId().equals(destination.getId());
    }
}