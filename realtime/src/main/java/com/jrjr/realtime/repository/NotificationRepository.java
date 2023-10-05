package com.jrjr.realtime.repository;

import com.jrjr.realtime.document.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findByUserSeqAndIsReadFalse(Long userSeq);

}

