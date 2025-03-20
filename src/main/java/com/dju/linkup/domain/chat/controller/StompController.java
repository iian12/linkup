package com.dju.linkup.domain.chat.controller;

import com.dju.linkup.domain.chat.model.ChatMessageDto;
import com.dju.linkup.domain.chat.RedisPubSubService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class StompController {

    private final RedisPubSubService redisPubSubService;

    public StompController(RedisPubSubService service) {
        this.redisPubSubService = service;
    }

    @MessageMapping("/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, ChatMessageDto chatMessageDto) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(chatMessageDto);
        redisPubSubService.publish("chat", message);
    }
}
