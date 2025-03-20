package com.dju.linkup.global.handler;

import com.dju.linkup.global.security.JwtTokenProvider;
import com.dju.linkup.global.security.TokenUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public StompHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");
            jwtTokenProvider.validateToken(token);
        }

        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");
            jwtTokenProvider.validateToken(token);

            String userId = TokenUtils.getUserIdFromToken(token);
        }

        return message;
    }
}
