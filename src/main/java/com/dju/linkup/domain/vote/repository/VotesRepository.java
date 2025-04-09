package com.dju.linkup.domain.vote.repository;

import com.dju.linkup.domain.vote.model.Votes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotesRepository extends JpaRepository<Votes, String> {
}
