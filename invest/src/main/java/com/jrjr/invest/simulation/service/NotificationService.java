package com.jrjr.invest.simulation.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jrjr.invest.simulation.document.Notification;
import com.jrjr.invest.simulation.repository.NotificationRepository;
import com.jrjr.invest.simulation.repository.SimulationRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

	@Value("${custom.rabbitmq.exchange}")
	private String EXCHANGE_NAME;

	@Value("${custom.rabbitmq.routing-key.invest}")
	private String INVEST_ROUTING_KEY;

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

		rabbitTemplate.convertAndSend(EXCHANGE_NAME, INVEST_ROUTING_KEY, notification.toNotificationDTO());
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

		rabbitTemplate.convertAndSend(EXCHANGE_NAME, INVEST_ROUTING_KEY, notification.toNotificationDTO());
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

		rabbitTemplate.convertAndSend(EXCHANGE_NAME, INVEST_ROUTING_KEY, notification.toNotificationDTO());
	}

}