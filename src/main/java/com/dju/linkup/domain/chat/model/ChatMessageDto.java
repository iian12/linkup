package com.dju.linkup.domain.chat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDto {

    private String content;
    private String roomId;
}
