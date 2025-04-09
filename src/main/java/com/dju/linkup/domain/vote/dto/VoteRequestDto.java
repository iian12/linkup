package com.dju.linkup.domain.vote.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VoteRequestDto {

    List<String> selectedOptionIds;
}
