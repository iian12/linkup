package com.dju.linkup.domain.post.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @Tsid
    private String id;

    private String title;
    private String content;
    private String userId;

    @ElementCollection
    private List<String> hashtagIds;

    private String thumbnailUrl;

    @ElementCollection
    private List<String> imgUrls;

    @Enumerated(EnumType.STRING)
    private PostTopic topic;

    @Enumerated(EnumType.STRING)
    private Available available;

    private LocalDateTime timeStamp;
    private boolean isUpdated;

    @Builder
    public Post(String title, String content, String userId, List<String> hashtagIds, String thumbnailUrl, List<String> imgUrls, PostTopic topic) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.hashtagIds = new ArrayList<>(hashtagIds);
        this.thumbnailUrl = thumbnailUrl;
        this.imgUrls = new ArrayList<>(imgUrls);
        this.topic = topic;
        this.timeStamp = LocalDateTime.now();
        this.isUpdated = false;
        this.available = Available.ACCESS;
    }

    public void updatePost(String title, String content, List<String> hashtagIds, List<String> imgUrls, PostTopic topic) {
        this.title = title;
        this.content = content;
        this.hashtagIds = hashtagIds;
        this.imgUrls = imgUrls;
        this.topic = topic;
    }
}
