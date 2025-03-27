package com.dju.linkup.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostListResDto {

    private String postId;
    private String title;
    private String content;
    private String authorNickname;
    private String thumbnailUrl;

    @Builder
    public PostListResDto(String postId, String title, String content, String authorNickname, String thumbnailUrl) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.authorNickname = authorNickname;
        this.thumbnailUrl = thumbnailUrl;
    }
}
