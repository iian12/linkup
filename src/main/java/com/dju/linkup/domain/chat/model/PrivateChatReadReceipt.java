package com.dju.linkup.domain.chat.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PrivateChatReadReceipt {

    @Id
    @Tsid
    private String id;

    private String messageId;
    private String receiverId;
    private LocalDateTime readAt;

    @Builder
    public PrivateChatReadReceipt(String messageId, String receiverId) {
        this.messageId = messageId;
        this.receiverId = receiverId;
        this.readAt = LocalDateTime.now();
    }
}
