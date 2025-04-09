package com.dju.linkup.domain.post.service;

import com.dju.linkup.domain.file.FileService;
import com.dju.linkup.domain.post.dto.PostRequestDto;
import com.dju.linkup.domain.post.dto.PostListResDto;
import com.dju.linkup.domain.post.model.Post;
import com.dju.linkup.domain.post.model.PostTopic;
import com.dju.linkup.domain.post.repository.PostRepository;
import com.dju.linkup.domain.user.model.Users;
import com.dju.linkup.domain.user.repository.UserRepository;
import com.dju.linkup.domain.vote.dto.PostWithVoteRequestDto;
import com.dju.linkup.domain.vote.model.VoteOption;
import com.dju.linkup.domain.vote.model.Votes;
import com.dju.linkup.domain.vote.repository.UserVoteRepository;
import com.dju.linkup.domain.vote.repository.VoteOptionRepository;
import com.dju.linkup.domain.vote.repository.VotesRepository;
import com.dju.linkup.global.security.TokenUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final VoteOptionRepository voteOptionRepository;
    private final VotesRepository votesRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, HashtagService hashtagService, FileService fileService, VoteOptionRepository voteOptionRepository, VotesRepository votesRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.hashtagService = hashtagService;
        this.fileService = fileService;
        this.voteOptionRepository = voteOptionRepository;
        this.votesRepository = votesRepository;
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

    public String createPost(PostRequestDto reqDto, String token) {
        Users user = findUserByToken(token);
        Post post = createBasePost(reqDto, user);
        postRepository.save(post);
        return post.getId();
    }

    public String createPostWithVote(PostWithVoteRequestDto reqDto, String token) {
        Users user = findUserByToken(token);
        Post post = createBasePost(reqDto, user);
        postRepository.save(post);

        createVote(reqDto, post.getId());

        return post.getId();
    }

    private Users findUserByToken(String token) {
        return userRepository.findById(TokenUtils.getUserIdFromToken(token))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private Post createBasePost(PostRequestDto reqDto, Users user) {
        List<String> hashtagIds = hashtagService.findOrCreateHashtags(reqDto.getHashtagNames());
        List<String> imageUrls = reqDto.getImages()
                .stream()
                .map(fileService::saveFile)
                .toList();

        PostTopic topic = PostTopic.fromString(reqDto.getTopic());

        return Post.builder()
                .title(reqDto.getTitle())
                .content(reqDto.getContent())
                .topic(topic)
                .userId(user.getId())
                .hashtagIds(hashtagIds)
                .imgUrls(imageUrls)
                .thumbnailUrl(imageUrls.isEmpty() ? null : imageUrls.getFirst())
                .build();
    }

    private void createVote(PostWithVoteRequestDto reqDto, String postId) {
        Votes vote = Votes.builder()
                .postId(postId)
                .question(reqDto.getVoteQuestion())
                .multipleChoice(reqDto.isMultipleChoice())
                .build();
        votesRepository.save(vote);

        List<VoteOption> voteOptions = reqDto.getVoteOptions().stream()
                .map(optionContent -> {
                    VoteOption voteOption = VoteOption.builder()
                            .voteId(vote.getId())
                            .content(optionContent)
                            .build();
                    voteOptionRepository.save(voteOption);
                    return voteOption;
                })
                .toList();

        voteOptionRepository.saveAll(voteOptions);
    }

    public String updatePost(String postId, PostRequestDto reqDto, String token) {
        Users user = findUserByToken(token);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!Objects.equals(user.getId(), post.getUserId())) {
            throw new IllegalArgumentException("User not authorized to update this post");
        }

        List<String> hashtagIds = hashtagService.findOrCreateHashtags(reqDto.getHashtagNames());
        List<String> imageUrls = reqDto.getImages()
                .stream()
                .map(fileService::saveFile)
                .toList();
        PostTopic topic = PostTopic.fromString(reqDto.getTopic());
        post.updatePost(reqDto.getTitle(), reqDto.getContent(), hashtagIds, imageUrls, topic);

        return post.getId();
    }

    public void deletePost(String postId, String token) {
        Users user = findUserByToken(token);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!Objects.equals(user.getId(), post.getUserId())) {
            throw new IllegalArgumentException("User not authorized to delete this post");
        }

        postRepository.delete(post);
    }
}
