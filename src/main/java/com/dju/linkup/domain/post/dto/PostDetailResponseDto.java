package com.dju.linkup.domain.post.dto;

import com.dju.linkup.domain.post.model.PostTopic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDetailResponseDto {

    private String title;
    private String content;
    private String userId;
    private List<String> hashtagIds;
    private List<String> imgUrls;
    private String topic;
    private LocalDateTime timeStamp;
    private boolean isUpdated;

    private
}
