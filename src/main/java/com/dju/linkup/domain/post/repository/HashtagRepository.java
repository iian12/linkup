package com.dju.linkup.domain.post.repository;

import com.dju.linkup.domain.post.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, String> {

    Optional<Hashtag> findByName(String name);
}
