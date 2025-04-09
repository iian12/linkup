package com.dju.linkup.domain.post.controller;

import com.dju.linkup.domain.post.dto.PostRequestDto;
import com.dju.linkup.domain.post.dto.PostListResDto;
import com.dju.linkup.domain.post.service.PostService;
import com.dju.linkup.domain.vote.dto.PostWithVoteRequestDto;
import com.dju.linkup.global.security.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostListResDto>> getPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.getPostList(page, size));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createPost(@ModelAttribute PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = TokenUtils.extractTokenFromRequest(request);

        String postId;
        if (postRequestDto instanceof PostWithVoteRequestDto) {
            postId = postService.createPostWithVote((PostWithVoteRequestDto) postRequestDto, token);
        } else {
            postId = postService.createPost(postRequestDto, token);
        }

        return ResponseEntity.ok(postId);
    }

    @PutMapping(path = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updatePost(@PathVariable String postId, @ModelAttribute PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = TokenUtils.extractTokenFromRequest(request);
        String id = postService.updatePost(postId, postRequestDto, token);

        return ResponseEntity.ok(id);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePost(@RequestParam String postId, HttpServletRequest request) {
        String token = TokenUtils.extractTokenFromRequest(request);
        postService.deletePost(postId, token);

        return ResponseEntity.ok().build();
    }
}
