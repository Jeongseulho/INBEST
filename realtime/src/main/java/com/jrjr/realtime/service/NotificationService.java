package com.jrjr.realtime.service;

import com.jrjr.realtime.document.Notification;
import com.jrjr.realtime.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messageTemplate;

    // 알림 수신 확인 표시 업데이트
    @Transactional
    public void updateReceivedNotification(String id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    // 안 읽은 알람 다시 보내기
    public void resendNotifications(Long userSeq) {
        List<Notification> notificationList = notificationRepository.findByUserSeqAndIsReadFalse(userSeq);
        for (Notification notification : notificationList) {
            messageTemplate.convertAndSend("/topic/notification/"+userSeq, notification.toNotificationDTO());
        }
    }
}
