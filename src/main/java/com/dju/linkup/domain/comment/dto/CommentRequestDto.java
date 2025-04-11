package com.dju.linkup.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private String content;
    private String parentCommentId;


    public CommentRequestDto(String content, String parentCommentId) {
        this.content = content;
        this.parentCommentId = parentCommentId;
    }
}
