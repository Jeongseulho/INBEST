package com.jrjr.invest.simulation.document;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.jrjr.invest.simulation.dto.notification.NotificationDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Document(collection = "notification")
public class Notification {

	@Id
	@Field(targetType = FieldType.OBJECT_ID)
	private String id;
	private Long simulationSeq;
	private String title;
	private Long userSeq;
	private String message;
	private Boolean isRead;
	private LocalDateTime dateTime;

	@Builder
	public Notification(Long userSeq, Long simulationSeq) {
		this.userSeq = userSeq;
		this.simulationSeq = simulationSeq;
		this.isRead = false;
		this.dateTime = LocalDateTime.now();
	}

	public void setInvititionMessage(String simulationTitle, String ownerNickname) {
		this.title = "모의 투자 초대 알림";
		this.message = ownerNickname + "님이 " + simulationTitle + "에 초대하셨습니다.";
	}

	public void setStartMessage(String simulationTitle) {
		this.title = "모의 투자 시작 알림";
		this.message = simulationTitle + "에서 모의 투자가 시작되었습니다!";
	}

	public void setFinishMessage(String simulationTitle) {
		this.title = "모의 투자 종료 알림";
		this.message = simulationTitle + "에서 모의 투자가 종료되었습니다!";
	}

	public NotificationDTO toNotificationDTO() {
		return NotificationDTO.builder()
			.id(id)
			.simulationSeq(simulationSeq)
			.userSeq(userSeq)
			.title(title)
			.message(message)
			.isRead(isRead)
			.dateTime(dateTime)
			.build();
	}
}
