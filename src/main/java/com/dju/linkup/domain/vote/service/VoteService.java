package com.dju.linkup.domain.vote.service;

import com.dju.linkup.domain.user.model.Users;
import com.dju.linkup.domain.user.repository.UserRepository;
import com.dju.linkup.domain.vote.dto.VoteRequestDto;
import com.dju.linkup.domain.vote.model.UserVote;
import com.dju.linkup.domain.vote.model.VoteOption;
import com.dju.linkup.domain.vote.repository.UserVoteRepository;
import com.dju.linkup.domain.vote.repository.VoteOptionRepository;
import com.dju.linkup.domain.vote.repository.VotesRepository;
import com.dju.linkup.global.security.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VoteService {

    private final UserRepository userRepository;
    private final UserVoteRepository userVoteRepository;
    private final VoteOptionRepository voteOptionRepository;

    public VoteService(UserRepository userRepository, UserVoteRepository userVoteRepository, VoteOptionRepository voteOptionRepository) {
        this.userRepository = userRepository;
        this.userVoteRepository = userVoteRepository;
        this.voteOptionRepository = voteOptionRepository;
    }

    public void vote(String postId, VoteRequestDto requestDto, String token) {

        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
                .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        if (userVoteRepository.existsByUserIdAndPostId(user.getId(), postId)) {
            throw new IllegalArgumentException("이미 투표함.");
        }

        List<VoteOption> selectedOptions = voteOptionRepository.findAllById(requestDto.getSelectedOptionIds());

        if (selectedOptions.size() != requestDto.getSelectedOptionIds().size()) {
            throw new IllegalArgumentException("유효하지 않은 선택지 포함.");
        }

        String voteId = selectedOptions.getFirst().getVoteId();

        boolean allSameVote = selectedOptions.stream()
                .allMatch(voteOption -> voteOption.getVoteId().equals(voteId));

        if (!allSameVote) {
            throw new IllegalArgumentException("모든 선택지는 동일한 투표에 속해야 함.");
        }

        UserVote userVote = UserVote.builder()
                .userId(user.getId())
                .postId(postId)
                .voteOptionIds(requestDto.getSelectedOptionIds())
                .build();

        userVoteRepository.save(userVote);

        for (String optionId : requestDto.getSelectedOptionIds()) {
            voteOptionRepository.incrementVoteCount(optionId);
        }
    }
}
