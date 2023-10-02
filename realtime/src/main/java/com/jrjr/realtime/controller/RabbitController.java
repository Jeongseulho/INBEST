package com.jrjr.realtime.controller;

import com.jrjr.realtime.dto.ChatDTO;
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

//    private final RabbitTemplate rabbitTemplate;
    private final SimpMessagingTemplate messageTemplate;


    @RabbitListener(queues = "invest-queue")
    public void receiveInvestMessage(NotificationDTO notificationDTO) {
        log.info("Invest Service에서 온 메세지: {}", notificationDTO.toString());
//        rabbitTemplate.convertAndSend("/topic/notification."+notificationDTO.getUserSeq(), notificationDTO);
        messageTemplate.convertAndSend("/topic/notification." + notificationDTO.getUserSeq(), notificationDTO);
    }


    @RabbitListener(queues = "trading-queue")
    public void receiveTradingMessage(NotificationDTO notificationDTO) {
        log.info("Trading Service에서 온 메세지: {}", notificationDTO.toString());
        messageTemplate.convertAndSend("/topic/notification." + notificationDTO.getUserSeq(), notificationDTO);
    }


    @RabbitListener(queues = "chat-queue")
    public void receiveChatMessage(ChatDTO chatDTO) {
        log.info("Chat Service에서 온 메세지: {}", chatDTO.toString());
        messageTemplate.convertAndSend("/topic/chat." + chatDTO.getSimulationSeq(), chatDTO);
    }

}