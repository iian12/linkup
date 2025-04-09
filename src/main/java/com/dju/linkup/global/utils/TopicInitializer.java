package com.dju.linkup.global.utils;

import com.dju.linkup.domain.TopicFollow.model.PostTopicEntity;
import com.dju.linkup.domain.TopicFollow.repository.PostTopicEntityRepository;
import com.dju.linkup.domain.post.model.PostTopic;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TopicInitializer {

    private final PostTopicEntityRepository postTopicEntityRepository;

    public TopicInitializer(PostTopicEntityRepository postTopicEntityRepository) {
        this.postTopicEntityRepository = postTopicEntityRepository;
    }

    @PostConstruct
    public void init() {
        for (PostTopic topic : PostTopic.values()) {
            if (!postTopicEntityRepository.existsById(topic.name())) {
                postTopicEntityRepository.save(new PostTopicEntity(topic));
            }
        }
    }
}
