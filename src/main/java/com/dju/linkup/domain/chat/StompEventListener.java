package com.dju.linkup.domain.chat;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StompEventListener {

    private final Set<String> session = ConcurrentHashMap.newKeySet();

    @EventListener
    public void connectHandler(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        session.add(accessor.getSessionId());
    }

    @EventListener
    public void disconnectHandle(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        session.remove(accessor.getSessionId());
    }
}
