package com.dju.linkup.domain.comment.repository;

import com.dju.linkup.domain.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
}
