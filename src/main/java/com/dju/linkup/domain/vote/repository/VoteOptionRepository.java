package com.dju.linkup.domain.vote.repository;

import com.dju.linkup.domain.vote.model.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteOptionRepository extends JpaRepository<VoteOption, String> {

    @Modifying
    @Query("UPDATE VoteOption vo SET vo.userCount = vo.userCount + 1 WHERE vo.id = :id")
    void incrementVoteCount(@Param("id") String id);
}
