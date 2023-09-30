package com.jrjr.realtime.test;

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
