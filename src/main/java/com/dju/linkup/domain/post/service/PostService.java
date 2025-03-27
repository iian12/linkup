package com.dju.linkup.domain.post.service;

import com.dju.linkup.domain.file.FileService;
import com.dju.linkup.domain.post.dto.CreatePostDto;
import com.dju.linkup.domain.post.dto.PostListResDto;
import com.dju.linkup.domain.post.model.Post;
import com.dju.linkup.domain.post.model.PostTopic;
import com.dju.linkup.domain.post.repository.PostRepository;
import com.dju.linkup.domain.user.model.Users;
import com.dju.linkup.domain.user.repository.UserRepository;
import com.dju.linkup.global.security.TokenUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HashtagService hashtagService;
    private final FileService fileService;

    public PostService(PostRepository postRepository, UserRepository userRepository, HashtagService hashtagService, FileService fileService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.hashtagService = hashtagService;
        this.fileService = fileService;
    }

    @Transactional(readOnly = true)
    public List<PostListResDto> getPostList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Order.desc("createdAt")));
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.stream().map(post -> {
            String authorNickname = userRepository.findById(post.getUserId())
                    .map(Users::getNickname).orElse("Unknown");
            return PostListResDto.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .authorNickname(authorNickname)
                    .build();
        }).collect(Collectors.toList());
    }

    public String createPost(CreatePostDto reqDto, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<String> hashtagIds = hashtagService.findOrCreateHashtags(reqDto.getHashtagNames());
        List<String> imageUrls = reqDto.getImages()
                .stream().map(fileService::saveFile).toList();

        PostTopic topic = PostTopic.fromString(reqDto.getTopic());

        Post post = Post.builder()
                .title(reqDto.getTitle())
                .content(reqDto.getContent())
                .topic(topic)
                .userId(user.getId())
                .hashtagIds(hashtagIds)
                .imgUrls(imageUrls)
                .thumbnailUrl(imageUrls.isEmpty() ? null : imageUrls.getFirst())
                .build();

        postRepository.save(post);
        return post.getId();
    }

    public void deletePost(String postId, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (Objects.equals(user.getId(), post.getUserId())) {
            postRepository.delete(post);
        }
    }
}
