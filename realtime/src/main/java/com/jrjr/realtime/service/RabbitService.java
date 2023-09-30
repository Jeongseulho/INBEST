package com.jrjr.realtime.service;

import com.jrjr.realtime.test.MessageDTO;
import com.jrjr.realtime.dto.NotificationDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class RabbitService {

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
    public void receiveMessage(NotificationDTO notificationDTO) {
        log.info("invest Received message: {}", notificationDTO.toString());
        messageTemplate.convertAndSend("/topic/notification/"+notificationDTO.getUserSeq(), notificationDTO);
    }


}