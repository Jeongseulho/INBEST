package com.jrjr.chat.controller;

import com.jrjr.chat.dto.ChatDTO;
import com.jrjr.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class RabbitController {

    private final RabbitTemplate rabbitTemplate;
    private final ChatService chatService;

    @Value("${custom.rabbitmq.exchange}")
    private String EXCHANGE_NAME;

    @Value("${custom.rabbitmq.routing-key.chat}")
    private String CHAT_ROUTING_KEY;

    @RabbitListener(queues = "realtime-queue")
    public void receiveRealTimeMessage(ChatDTO chatDTO) {
        log.info("RealTime Service에서 온 메세지: {}", chatDTO.toString());
        chatDTO = chatService.setMessage(chatDTO);
        chatDTO.setDateTime();
        chatDTO.setProfileImgSearchName(chatService.setProfileImage(chatDTO.getUserSeq()));
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, CHAT_ROUTING_KEY, chatDTO);
    }
}