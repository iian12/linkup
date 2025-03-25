package com.dju.linkup.domain.post.repository;

import com.dju.linkup.domain.post.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, String> {

}
