package com.dju.linkup.domain.chat.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "private_messages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PrivateChatMessage {

    @Id
    @Tsid
    private String id;

    private String roomId;
    private String senderId;
    private String content;
    private LocalDateTime timeStamp;

    @Builder
    public PrivateChatMessage(String roomId, String senderId, String content) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.content = content;
        this.timeStamp = LocalDateTime.now();
    }

}
