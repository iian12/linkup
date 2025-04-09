package com.dju.linkup.domain.TopicFollow.model;

import com.dju.linkup.domain.post.model.PostTopic;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTopicEntity {

    @Id
    @Enumerated(EnumType.STRING)
    private PostTopic postTopic;

    private int followUserCount;

    public PostTopicEntity(PostTopic postTopic) {
        this.postTopic = postTopic;
        followUserCount = 0;
    }

    public void addFollowUserCount() {
        followUserCount++;
    }

    public void deleteFollowUserCount() {
        followUserCount--;
    }
}
