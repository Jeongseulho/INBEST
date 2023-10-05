package com.jrjr.realtime.service;

import com.jrjr.realtime.document.Notification;
import com.jrjr.realtime.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // 알림 수신 확인 표시 업데이트
    @Transactional
    public void updateReceivedNotification(String id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    // 안 읽은 알람 다시 보내기
    public List<Notification> getUnreadNotifications(Long userSeq) {
         return notificationRepository.findByUserSeqAndIsReadFalse(userSeq);
    }
}
