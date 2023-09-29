package com.jrjr.realtime.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NotificationDTO {

    private String seq;
    private String receiver;
    private String message;
}