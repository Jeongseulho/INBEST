package com.jrjr.realtime.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
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
    private String message;
    private Boolean isRead;
    private LocalDateTime dateTime;

}
