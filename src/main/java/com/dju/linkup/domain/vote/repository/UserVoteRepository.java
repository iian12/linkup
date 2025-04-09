package com.dju.linkup.domain.vote.repository;

import com.dju.linkup.domain.vote.model.UserVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserVoteRepository extends JpaRepository<UserVote, Long> {

    boolean existsByUserIdAndPostId(String userId, String postId);

    @Query("SELECT COUNT(uv) FROM UserVote uv WHERE uv.postId = :postId")
    long countByPostId(@Param("postId") String postId);
}
