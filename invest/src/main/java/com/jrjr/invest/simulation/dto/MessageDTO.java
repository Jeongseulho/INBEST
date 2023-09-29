package com.jrjr.invest.simulation.dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class MessageDTO {
    private String title;
    private String content;
}
