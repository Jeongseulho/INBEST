package com.jrjr.realtime.controller;

import com.jrjr.realtime.document.Notification;
import com.jrjr.realtime.dto.ChatDTO;
import com.jrjr.realtime.dto.NotificationDTO;
import com.jrjr.realtime.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@AllArgsConstructor
public class StompChatController {


//    @Value("${rabbitmq.exchange.name}")
//    private String exchangeName;
//
//    @Value("${rabbitmq.routing.key}")
//    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    @Operation(summary = "채팅, 입장, 퇴장")
    @MessageMapping("/chat.message.{simulationSeq}")
    public void chat(@DestinationVariable Long simulationSeq, @Payload ChatDTO chatDTO) {
        log.info("[채팅, 입장, 퇴장]");
        log.info("simulationSeq " + simulationSeq);
        log.info(chatDTO.toString());
        rabbitTemplate.convertAndSend("realtime_direct", "chat", chatDTO);
    }

}