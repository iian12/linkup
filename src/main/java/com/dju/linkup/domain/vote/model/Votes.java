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
public class Votes {

    @Id
    @Tsid
    private String id;

    private String postId;
    private String question;
    private boolean multipleChoice;

    @Builder
    public Votes(String postId, String question, boolean multipleChoice) {
        this.postId = postId;
        this.question = question;
        this.multipleChoice = multipleChoice;
    }
}
