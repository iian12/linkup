package com.dju.linkup.domain.TopicFollow.service;

import com.dju.linkup.domain.TopicFollow.model.PostTopicEntity;
import com.dju.linkup.domain.TopicFollow.model.UserTopicFollow;
import com.dju.linkup.domain.TopicFollow.repository.PostTopicEntityRepository;
import com.dju.linkup.domain.TopicFollow.repository.UserTopicFollowRepository;
import com.dju.linkup.domain.user.model.Users;
import com.dju.linkup.domain.user.repository.UserRepository;
import com.dju.linkup.global.security.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TopicFollowService {

    private final UserRepository userRepository;
    private final PostTopicEntityRepository postTopicEntityRepository;
    private final UserTopicFollowRepository userTopicFollowRepository;

    public TopicFollowService(UserRepository userRepository, PostTopicEntityRepository postTopicEntityRepository, UserTopicFollowRepository userTopicFollowRepository) {
        this.userRepository = userRepository;
        this.postTopicEntityRepository = postTopicEntityRepository;
        this.userTopicFollowRepository = userTopicFollowRepository;
    }

    public void toggleFollowTopic(String token, String topicId) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
                .orElseThrow(() -> new IllegalArgumentException("Invalid User"));


        if (!postTopicEntityRepository.existsById(topicId)) {
            throw new IllegalArgumentException("Invalid Topic");
        }

       Optional<UserTopicFollow> topicFollowEntity = userTopicFollowRepository.findByUserIdAndPostTopicId(user.getId(), topicId);

       if (topicFollowEntity.isPresent()) {
           UserTopicFollow userTopicFollow = topicFollowEntity.get();
           userTopicFollowRepository.delete(userTopicFollow);
       } else {
           UserTopicFollow topicFollow = UserTopicFollow.builder()
                   .userId(user.getId())
                   .postTopicId(topicId)
                   .build();
           userTopicFollowRepository.save(topicFollow);
       }
    }
}
