package com.jrjr.chat.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@AllArgsConstructor
public class ChatDTO {
    private String type;
    private Long simulationSeq;
    private Long userSeq;
    private String message;
    private LocalDateTime dateTime;

    public void setChatMessage(String userNickname, String type) {
        this.message = userNickname + "님이 " + type + "하셨습니다.";
    }

    public void setDateTime() {
        this.dateTime = LocalDateTime.now();
    }

}
