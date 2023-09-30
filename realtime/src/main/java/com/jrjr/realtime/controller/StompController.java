package com.jrjr.realtime.controller;


import com.jrjr.realtime.dto.NotificationDTO;
import com.jrjr.realtime.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
public class StompController {

//    private final RabbitTemplate rabbitTemplate;
//    private final SimpMessagingTemplate simpMessagingTemplate;

    private final NotificationService notificationService;

    @Operation(summary = "알림 수신 여부 확인")
    @MessageMapping("/notification.user.{userSeq}")
    public void checkNotificationReceive(@DestinationVariable Long userSeq, @RequestBody NotificationDTO notificationDTO) {
        log.info("[알림 수신 여부 확인]");
        log.info("userSeq"+userSeq);
        log.info(notificationDTO.toString());
        notificationService.updateReceivedNotification(notificationDTO.getId());
    }

    @Operation(summary = "알림 다시 보내기")
    @SubscribeMapping("/notification/{userSeq}")
    public void resendNotifications(@DestinationVariable Long userSeq) {
        notificationService.resendNotifications(userSeq);
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