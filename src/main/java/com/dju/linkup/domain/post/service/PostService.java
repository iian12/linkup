package com.dju.linkup.domain.post.service;

import com.dju.linkup.domain.post.dto.CreatePostDto;
import com.dju.linkup.domain.post.model.Post;
import com.dju.linkup.domain.post.repository.PostRepository;
import com.dju.linkup.domain.user.model.Users;
import com.dju.linkup.domain.user.repository.UserRepository;
import com.dju.linkup.global.security.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public String createPost(CreatePostDto createPostDto, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = Post.builder()
            .title(createPostDto.getTitle())
            .content(createPostDto.getContent())
            .userId(user.getId())
            .build();

        postRepository.save(post);
        return post.getId();
    }
}
