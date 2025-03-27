package com.dju.linkup.domain.post.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class CreatePostDto {
    private String title;
    private String content;
    private String topic;
    private List<String> hashtagNames = new ArrayList<>();
    private List<MultipartFile> images = new ArrayList<>();

    public CreatePostDto(String title, String content, String topic, List<String> hashtagNames, List<MultipartFile> images) {
        this.title = title;
        this.content = content;
        this.topic = topic;
        this.hashtagNames = hashtagNames;
        this.images = images;
    }
}
