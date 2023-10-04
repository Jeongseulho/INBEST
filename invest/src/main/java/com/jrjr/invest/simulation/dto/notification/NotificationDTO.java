package com.jrjr.invest.simulation.dto.notification;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class NotificationDTO {
	private String id;
	private String title;
	private Long simulationSeq;
	private Long userSeq;
	private String message;
	private Boolean isRead;
	private LocalDateTime dateTime;
}
