package com.dju.linkup.domain.TopicFollow.repository;

import com.dju.linkup.domain.TopicFollow.model.UserTopicFollow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTopicFollowRepository extends JpaRepository<UserTopicFollow, Long> {

    Optional<UserTopicFollow> findByUserIdAndPostTopicId(String userId, String postTopicId);
}
