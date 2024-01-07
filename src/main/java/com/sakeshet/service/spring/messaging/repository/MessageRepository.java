package com.sakeshet.service.spring.messaging.repository;

import com.sakeshet.service.spring.messaging.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByreceiverUsernameAndReadFalse(String toUsername);

    @Transactional
    @Modifying
    @Query("UPDATE Message m SET m.read = true WHERE m.receiverUsername = :toUsername AND m.read = false")
    void markMessagesAsRead(@Param("toUsername") String toUsername);
}
