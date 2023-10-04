package com.jrjr.inbest.trading.document;

import com.jrjr.inbest.trading.dto.NotificationDTO;
import com.jrjr.inbest.trading.dto.TradingDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Document(collection = "notification")
public class Notification {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;
    private Long simulationSeq;
    private Long userSeq;
    private String title;
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

    public void setApplyFailMessage() {
        this.message = "현재 장이 열려있지 않습니다!";
    }

    public void setApplyTradingMessage(String simulationTitle, TradingDTO tradingDTO) {
        String tradingType = tradingDTO.getTradingType() == 0 ? "매도" : "매수";
        this.message = simulationTitle+ "에서 " + tradingDTO.getStockName() + " " + tradingDTO.getAmount() + "주 " +
            tradingDTO.getPrice() + "원(KRW)에 " + tradingType + " 신청이 완료되었습니다.";
    }

    public void setTradingMessage(String simulationTitle, TradingDTO tradingDTO) {

        // 매매 타입
        String tradingType = tradingDTO.getTradingType() == 0 ? "매도" : "매수";

        // 성공 여부
        String conclusionType = "미체결";
        if (tradingDTO.getConclusionType() == 1) {
            this.title = "주식 체결 성공 알림";
            conclusionType = "성공";
        } else if (tradingDTO.getConclusionType() == 2) {
            this.title = "주식 체결 실패 알림";
            conclusionType = "실패";
        }

        // 가상화페인 경우
        String amountType = tradingDTO.getStockType() == 2 ? "개" : "주";

        // 가격 쉼표 처리
        DecimalFormat decFormat = new DecimalFormat("###,###");

        this.message = "모의투자(" + simulationTitle+ ")에서 " + tradingDTO.getStockName() + " " + tradingDTO.getAmount()
                + amountType + "를 " + decFormat.format(tradingDTO.getPrice() * tradingDTO.getAmount()) + "원(KRW)에 " + tradingType + " " + conclusionType  + "하였습니다.";
    }

    public NotificationDTO toNotificationDTO() {
        return NotificationDTO.builder()
                .id(id)
                .simulationSeq(simulationSeq)
                .userSeq(userSeq)
                .message(message)
                .title(title)
                .isRead(isRead)
                .dateTime(dateTime)
                .build();
    }
}
