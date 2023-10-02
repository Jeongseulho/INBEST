package com.jrjr.inbest.trading.service;

import com.jrjr.inbest.trading.document.Notification;
import com.jrjr.inbest.trading.dto.TradingDTO;
import com.jrjr.inbest.trading.repository.NotificationRepository;
import com.jrjr.inbest.trading.repository.TradingRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class NotificationService {

    private final RabbitTemplate rabbitTemplate;
    private final TradingRepository tradingRepository;
    private final NotificationRepository notificationRepository;

    // 매매 신청 완료 알림 보내기
    @Transactional
    public void sendApplyTradingMessage(TradingDTO tradingDTO) {
        log.info("[매매 신청 완료 알림 보내기]");

        String simulationTitle = tradingRepository.getSimulationTitle(tradingDTO.getSeq());
        Notification notification = Notification.builder()
                .simulationSeq(tradingDTO.getSimulationSeq())
                .userSeq(tradingDTO.getUserSeq())
                .build();
        notification.setApplyTradingMessage(simulationTitle, tradingDTO);
        notificationRepository.save(notification);

        rabbitTemplate.convertAndSend("realtime_direct", "trading", notification.toNotificationDTO());
    }

    // 매매 성공, 실패 알림 보내기
    @Transactional
    public void sendTradingMessage(TradingDTO tradingDTO) {
        log.info("[매매 성공, 실패 알림 보내기]");

        String simulationTitle = tradingRepository.getSimulationTitle(tradingDTO.getSeq());
        Notification notification = Notification.builder()
                .simulationSeq(tradingDTO.getSimulationSeq())
                .userSeq(tradingDTO.getUserSeq())
                .build();
        notification.setTradingMessage(simulationTitle, tradingDTO);
        notificationRepository.save(notification);

        rabbitTemplate.convertAndSend("realtime_direct", "trading", notification.toNotificationDTO());
    }
}
