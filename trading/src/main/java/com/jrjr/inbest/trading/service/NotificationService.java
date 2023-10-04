package com.jrjr.inbest.trading.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jrjr.inbest.trading.document.Notification;
import com.jrjr.inbest.trading.dto.TradingDTO;
import com.jrjr.inbest.trading.repository.NotificationRepository;
import com.jrjr.inbest.trading.repository.TradingRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    @Value("${custom.rabbitmq.exchange}")
    private String EXCHANGE_NAME;

    @Value("${custom.rabbitmq.routing-key.trading}")
    private String TRADING_ROUTING_KEY;

    private final RabbitTemplate rabbitTemplate;
    private final TradingRepository tradingRepository;
    private final NotificationRepository notificationRepository;

    // 매매 신청 실패 알림 보내기
    @Transactional
    public void sendApplyFailMessage(TradingDTO tradingDTO) {
        log.info("[매매 신청 실패 알림 보내기]");

        Notification notification = Notification.builder()
                .simulationSeq(tradingDTO.getSimulationSeq())
                .userSeq(tradingDTO.getUserSeq())
                .build();
        notification.setApplyFailMessage();
        notificationRepository.save(notification);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, TRADING_ROUTING_KEY, notification.toNotificationDTO());
    }

    // 매매 신청 완료 알림 보내기
    @Transactional
    public void sendApplySuccessMessage(TradingDTO tradingDTO) {
        log.info("[매매 신청 완료 알림 보내기]");

        String simulationTitle = tradingRepository.getSimulationTitle(tradingDTO.getSeq());
        Notification notification = Notification.builder()
                .simulationSeq(tradingDTO.getSimulationSeq())
                .userSeq(tradingDTO.getUserSeq())
                .build();
        notification.setApplyTradingMessage(simulationTitle, tradingDTO);
        notificationRepository.save(notification);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, TRADING_ROUTING_KEY, notification.toNotificationDTO());
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

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, TRADING_ROUTING_KEY, notification.toNotificationDTO());
    }
}
