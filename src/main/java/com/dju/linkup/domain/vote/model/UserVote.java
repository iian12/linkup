package com.dju.linkup.domain.vote.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postId;
    private String userId;

    @ElementCollection
    private List<String> voteOptionIds;

    @Builder
    public UserVote(String postId, String userId, List<String> voteOptionIds) {
        this.postId = postId;
        this.userId = userId;
        this.voteOptionIds = new ArrayList<>(voteOptionIds);
    }
}
