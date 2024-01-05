package com.sakeshet.service.spring.messaging.repository;

import com.sakeshet.service.spring.messaging.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
