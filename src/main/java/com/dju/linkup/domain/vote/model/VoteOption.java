package com.dju.linkup.domain.vote.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteOption {

    @Id
    @Tsid
    private String id;

    private String voteId;
    private String content;
    private int userCount;

    @Builder
    public VoteOption(String voteId, String content) {
        this.voteId = voteId;
        this.content = content;
        this.userCount = 0;
    }
}
