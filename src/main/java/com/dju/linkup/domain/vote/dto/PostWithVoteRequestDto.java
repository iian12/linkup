package com.dju.linkup.domain.vote.dto;

import com.dju.linkup.domain.post.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostWithVoteRequestDto extends PostRequestDto {

    private boolean multipleChoice;
    private String voteQuestion;
    private List<String> voteOptions;
}
