package com.jrjr.invest.simulation.service;


import com.jrjr.invest.simulation.document.Notification;
import com.jrjr.invest.simulation.dto.notification.MessageDTO;
import com.jrjr.invest.simulation.repository.NotificationRepository;
import com.jrjr.invest.simulation.repository.SimulationRepository;
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
    private final SimulationRepository simulationRepository;
    private final NotificationRepository notificationRepository;

    // 초대 요청 알림 보내기
    @Transactional
    public void inviteUser(Long simulationSeq, String ownerNickname, Long userSeq) {
        log.info("[초대 요청 알림 보내기]");

        String simulationTitle = simulationRepository.findBySeq(simulationSeq).getTitle();
        Notification notification = Notification.builder()
                .simulationSeq(simulationSeq)
                .userSeq(userSeq)
                .build();
        notification.setInvititionMessage(simulationTitle, ownerNickname);
        notificationRepository.save(notification);

        rabbitTemplate.convertAndSend("realtime_direct", "invest", notification.toNotificationDTO());
    }

    // 시뮬레이션 시작 알림 메세지 보내기
    @Transactional
    public void sendStartNotification(Long simulationSeq, Long userSeq) {
        log.info("[시뮬레이션 시작 알림 메세지 보내기]");

        String simulationTitle = simulationRepository.findBySeq(simulationSeq).getTitle();
        Notification notification = Notification.builder()
                .simulationSeq(simulationSeq)
                .userSeq(userSeq)
                .build();
        notification.setStartMessage(simulationTitle);
        notificationRepository.save(notification);

        rabbitTemplate.convertAndSend("realtime_direct", "invest", notification.toNotificationDTO());
    }

    @Transactional
    public void sendFinishNotification(Long simulationSeq, Long userSeq) {
        log.info("[시뮬레이션 종료 알림 메세지 보내기]");

        String simulationTitle = simulationRepository.findBySeq(simulationSeq).getTitle();
        Notification notification = Notification.builder()
                .simulationSeq(simulationSeq)
                .userSeq(userSeq)
                .build();
        notification.setFinishMessage(simulationTitle);
        notificationRepository.save(notification);

        rabbitTemplate.convertAndSend("realtime_direct", "invest", notification.toNotificationDTO());
    }

}