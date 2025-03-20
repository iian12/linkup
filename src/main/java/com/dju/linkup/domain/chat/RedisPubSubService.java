package com.dju.linkup.domain.chat;

import com.dju.linkup.domain.chat.model.ChatMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisPubSubService implements MessageListener {

    private final StringRedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    public RedisPubSubService(@Qualifier("chatPubSub") StringRedisTemplate redisTemplate, SimpMessageSendingOperations messagingTemplate) {
        this.redisTemplate = redisTemplate;
        this.messagingTemplate = messagingTemplate;
    }

    public void publish(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String payload = new String(message.getBody());
        ObjectMapper mapper = new ObjectMapper();

        try {
            ChatMessageDto chatMessageDto = mapper.readValue(payload, ChatMessageDto.class);
            messagingTemplate.convertAndSend(chatMessageDto.getRoomId(), chatMessageDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
