package com.example.CryptoTreasures.webSocket;

import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CryptoTreasuresWebSocketHandler implements WebSocketHandler {

    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private final Map<String, WebSocketSession> moderatorSessions = new ConcurrentHashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Нова WebSocket връзка установена: " + session.getId());
        String initialMessage = "Hello, how can I assist you?";
        session.sendMessage(new TextMessage(initialMessage));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            String payload = ((TextMessage) message).getPayload();

            if (isUserSession(session)) {
                // Препратете съобщението от потребителя към модератор
                forwardMessageToModerator(session, payload);
            } else if (isModeratorSession(session)) {
                // Препратете отговора от модератор към потребителя
                forwardMessageToUser(session, payload);
            }
        }

    }

    private boolean isUserSession(WebSocketSession session) {
        return session.getAttributes().get("role").equals("user");
    }

    private boolean isModeratorSession(WebSocketSession session) {

        return session.getAttributes().get("role").equals("moderator");
    }

    private void forwardMessageToModerator(WebSocketSession userSession, String message) {
        for (WebSocketSession modSession : moderatorSessions.values()) {
            try {
                if (modSession.isOpen()) {
                    modSession.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                System.err.println("Грешка при изпращане на съобщението до модератор: " + e.getMessage());
                // Допълнителна логика за обработка на грешката, като например премахване на сесията
            }
        }
    }

    private void forwardMessageToUser(WebSocketSession moderatorSession, String message) {

    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
