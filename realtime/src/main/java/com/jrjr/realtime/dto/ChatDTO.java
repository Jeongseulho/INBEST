package com.jrjr.realtime.dto;

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
}
