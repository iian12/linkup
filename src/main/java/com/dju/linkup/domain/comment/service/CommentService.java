package com.dju.linkup.domain.comment.service;

import com.dju.linkup.domain.comment.dto.CommentRequestDto;
import com.dju.linkup.domain.comment.model.Comment;
import com.dju.linkup.domain.comment.repository.CommentRepository;
import com.dju.linkup.domain.post.model.Post;
import com.dju.linkup.domain.post.repository.PostRepository;
import com.dju.linkup.domain.user.model.Users;
import com.dju.linkup.domain.user.repository.UserRepository;
import com.dju.linkup.global.security.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(UserRepository userRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public String createComment(String postId, CommentRequestDto reqDto, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
                .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post"));

        if (reqDto.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(reqDto.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Parent Comment"));

            if (parentComment.isReply()) {
                throw new IllegalArgumentException("Parent comment is reply");
            }
        }

        Comment comment = Comment.builder()
                .content(reqDto.getContent())
                .postId(post.getId())
                .userId(user.getId())
                .parentCommentId(reqDto.getParentCommentId())
                .build();

        commentRepository.save(comment);
        return comment.getId();
    }
}
