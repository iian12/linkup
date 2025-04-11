package com.dju.linkup.domain.comment.model;

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
public class Comment {

    @Id
    @Tsid
    private String id;

    private String postId;
    private String userId;
    private String content;
    private String parentCommentId;

    private LocalDateTime createdAt;

    @Builder
    public Comment(String postId, String userId, String content, String parentCommentId) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.parentCommentId = parentCommentId;
        this.createdAt = LocalDateTime.now();
    }

    public boolean isReply() {
        return parentCommentId != null;
    }
}
