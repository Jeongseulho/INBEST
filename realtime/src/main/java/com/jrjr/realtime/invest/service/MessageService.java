package com.jrjr.realtime.invest.service;

import com.jrjr.realtime.invest.dto.MessageDTO;
import com.jrjr.realtime.invest.dto.NotificationDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class MessageService {

//    @Value("${rabbitmq.exchange.name}")
//    private String exchangeName;
//
//    @Value("${rabbitmq.routing.key}")
//    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    private final SimpMessagingTemplate simpMessagingTemplate;


    /**
     * Queue로 메시지를 발행
     *
     * @param messageDTO 발행할 메시지의 DTO 객체
     */
    public void sendMessage(MessageDTO messageDTO) {
        log.info("invest message sent: {}", messageDTO.toString());
        rabbitTemplate.convertAndSend("realtime_direct", "invest", messageDTO);
    }

    /**
     * Queue에서 메시지를 구독
     *
     * @param notificationDTO 구독한 메시지를 담고 있는 MessageDto 객체
     */
    @RabbitListener(queues = "invest-queue")
    public void receiveMessage(NotificationDTO notificationDTO) {
        log.info("invest Received message: {}", notificationDTO.toString());
        simpMessagingTemplate.convertAndSend("/topic/test", notificationDTO);
    }
}