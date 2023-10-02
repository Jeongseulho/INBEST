package com.jrjr.realtime.controller;

import com.jrjr.realtime.test.MessageDTO;
import com.jrjr.realtime.dto.NotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class RabbitController {

//    @Value("${rabbitmq.exchange.name}")
//    private String exchangeName;
//
//    @Value("${rabbitmq.routing.key}")
//    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    private final SimpMessagingTemplate messageTemplate;


    public void sendMessage(MessageDTO messageDTO) {
        log.info("invest message sent: {}", messageDTO.toString());
        rabbitTemplate.convertAndSend("realtime_direct", "invest", messageDTO);
    }


    @RabbitListener(queues = "invest-queue")
    public void receiveInvestMessage(NotificationDTO notificationDTO) {
        log.info("invest-queue Received message: {}", notificationDTO.toString());
//        rabbitTemplate.convertAndSend("/topic/notification."+notificationDTO.getUserSeq(), notificationDTO);
        messageTemplate.convertAndSend("/topic/notification."+notificationDTO.getUserSeq(), notificationDTO);
    }


    @RabbitListener(queues = "trading-queue")
    public void receiveTradingMessage(NotificationDTO notificationDTO) {
        log.info("trading-queue Received message: {}", notificationDTO.toString());
//        rabbitTemplate.convertAndSend("/topic/notification."+notificationDTO.getUserSeq(), notificationDTO);
        messageTemplate.convertAndSend("/topic/notification."+notificationDTO.getUserSeq(), notificationDTO);
    }


}