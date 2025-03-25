package com.dju.linkup.domain.post.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePostDto {
    private String title;
    private String content;
    private String topic;
    private List<String> hashtagNames;

    public CreatePostDto(String title, String content, String topic, List<String> hashtagNames) {
        this.title = title;
        this.content = content;
        this.topic = topic;
        this.hashtagNames = hashtagNames;
    }
}
