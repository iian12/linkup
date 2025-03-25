package com.dju.linkup.domain.post.repository;

import com.dju.linkup.domain.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String> {

}
