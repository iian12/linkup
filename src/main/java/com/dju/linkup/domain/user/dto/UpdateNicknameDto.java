package com.dju.linkup.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateNicknameDto {
    private String nickname;

    public UpdateNicknameDto(String nickname) {
        this.nickname = nickname;
    }
}
