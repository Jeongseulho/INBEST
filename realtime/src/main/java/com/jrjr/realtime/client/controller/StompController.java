package com.jrjr.realtime.client.controller;


import com.jrjr.realtime.invest.dto.NotificationDTO;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
public class StompController {

    private final RabbitTemplate rabbitTemplate;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/notification.user.{userSeq}")
    public void sendMessage(@DestinationVariable Long userSeq, @RequestBody NotificationDTO notificationDTO) {
        log.info("[메시지 수신]");
        log.info("userSeq"+userSeq);
        log.info(notificationDTO.toString());
    }

//    @PostMapping("/notification/user/{userSeq}")
//    public void sendMessage(@PathVariable Long userSeq, @RequestBody NotificationDTO notificationDTO) {
//        log.info("[메시지 수신]");
//        log.info("userSeq"+userSeq);
//        log.info(notificationDTO.toString());
//        rabbitTemplate.convertAndSend("topic", "/user", notificationDTO);
//        rabbitTemplate.convertAndSend("topic", "user", notificationDTO);
////        rabbitTemplate.convertAndSend("topic", "user", notificationDTO);
////        rabbitTemplate.convertAndSend("topic", "user", notificationDTO);
////        rabbitTemplate.convertAndSend("topic", "user", notificationDTO);
////        rabbitTemplate.convertAndSend("topic", "user", notificationDTO);
////        rabbitTemplate.convertAndSend("topic", "user."+userSeq, notificationDTO);
////        simpMessagingTemplate.convertAndSend("/topic/user."+userSeq, notificationDTO );
//    }

//    @PostMapping("/notification/user/{userSeq}")
//    public void sendMessage(@PathVariable Long userSeq, @RequestBody NotificationDTO notificationDTO) {
//        log.info("[메시지 수신]");
//        log.info("userSeq"+userSeq);
//        log.info(notificationDTO.toString());
//        rabbitTemplate.convertAndSend("/topic/user.{userSeq}", notificationDTO);
//    }


//    @MessageMapping("/notification.user.{userSeq}")
//    public void sendMessage(@DestinationVariable Long userSeq, NotificationDTO notificationDTO) {
//        log.info("[메시지 수신]");
//        log.info("userSeq"+userSeq);
//        log.info(notificationDTO.toString());
//        simpMessagingTemplate.convertAndSend("/topic/user."+userSeq, notificationDTO );
////        rabbitTemplate.convertAndSend("notification.exchange", "user."+userSeq, notificationDTO);
//    }

//    @MessageMapping("notification.user.{userSeq}")
//    @SendTo("/topic/user.{userSeq}")
//    public NotificationDTO sendMessage(@DestinationVariable Long userSeq, NotificationDTO notificationDTO) {
//        log.info("[메시지 수신]");
//        log.info("userSeq"+userSeq);
//        log.info(notificationDTO.toString());
//        return notificationDTO;
//    }
}