package com.jrjr.realtime.controller;

import com.jrjr.realtime.config.RabbitMqConfig;
import com.jrjr.realtime.document.Notification;
import com.jrjr.realtime.dto.ChatDTO;
import com.jrjr.realtime.dto.NotificationDTO;
import com.jrjr.realtime.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
public class StompChatController {


    @Value("${custom.rabbitmq.exchange}")
    private String EXCHANGE_NAME;

    @Value("${custom.rabbitmq.routing-key.chat}")
    private String CHAT_ROUTING_KEY;

    private final RabbitTemplate rabbitTemplate;

    @Operation(summary = "채팅, 입장, 퇴장")
    @MessageMapping("/chat.message.{simulationSeq}")
    public void chat(@DestinationVariable Long simulationSeq, @Payload ChatDTO chatDTO) {
        log.info("[채팅, 입장, 퇴장]");
        log.info("simulationSeq " + simulationSeq);
        log.info(chatDTO.toString());
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, CHAT_ROUTING_KEY, chatDTO);
    }

}