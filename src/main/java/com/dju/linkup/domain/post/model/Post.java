package com.dju.linkup.domain.post.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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

    @Enumerated(EnumType.STRING)
    private Available available;

    @Builder
    public Post(String title, String content, String userId, List<String> hashtagIds) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.hashtagIds = new ArrayList<>(hashtagIds);
        this.available = Available.ACCESS;
    }
}
