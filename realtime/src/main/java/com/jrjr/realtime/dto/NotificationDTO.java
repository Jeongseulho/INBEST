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
public class NotificationDTO {
    private String id;
    private Long simulationSeq;
    private Long userSeq;
    private String title;
    private String message;
    private Boolean isRead;
    private LocalDateTime dateTime;

}
