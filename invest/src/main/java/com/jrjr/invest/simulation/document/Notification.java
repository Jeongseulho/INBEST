package com.jrjr.invest.simulation.document;


import com.jrjr.invest.simulation.dto.notification.NotificationDTO;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Document(collection = "invititionNotification")
public class Notification {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String _id;
    private Long simulationSeq;
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
        this.message = ownerNickname + "님이 "  + simulationTitle + "에 초대하셨습니다.";
    }

    public void setStartMessage(String simulationTitle) {
        this.message = simulationTitle + "에서 모의 투자가 시작되었습니다!";
    }

    public void setFinishMessage(String simulationTitle) {
        this.message = simulationTitle + "에서 모의 투자가 종료되었습니다!";
    }

    public void sellingSuccessMessage(String simulationTitle, String userNickname) {
        this.message = simulationTitle + "에서 " + userNickname + "님이 "  +  "매도에 성공하였습니다.";
    }

    public void sellingFailMessage(String simulationTitle, String userNickname) {
        this.message = simulationTitle + "에서 " + userNickname + "님이 "  +  "매도에 실패하였습니다.";
    }

    public void buyingSuccessMessage(String simulationTitle, String userNickname) {
        this.message = simulationTitle + "에서 " + userNickname + "님이 "  +  "매수에 성공하였습니다.";
    }

    public void buyingFailMessage(String simulationTitle, String userNickname) {
        this.message = simulationTitle + "에서 " + userNickname + "님이 "  +  "매수에 실패하였습니다.";
    }

    public NotificationDTO toNotificationDTO() {
        return NotificationDTO.builder()
                ._id(_id)
                .simulationSeq(simulationSeq)
                .userSeq(userSeq)
                .message(message)
                .isRead(isRead)
                .dateTime(dateTime)
                .build();
    }
}
