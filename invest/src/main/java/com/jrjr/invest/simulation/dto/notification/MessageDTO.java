package com.jrjr.invest.simulation.dto.notification;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String title;
    private String content;
}
