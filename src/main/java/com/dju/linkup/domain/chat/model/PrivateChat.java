package com.dju.linkup.domain.chat.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PrivateChat {

    @Id
    @Tsid
    private String id;

    private String user1Id;
    private String user2Id;

    @Builder
    public PrivateChat(String user1Id, String user2Id) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }
}
