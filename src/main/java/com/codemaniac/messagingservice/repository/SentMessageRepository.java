package com.codemaniac.messagingservice.repository;

import com.codemaniac.messagingservice.model.SentMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentMessageRepository extends JpaRepository<SentMessage, Long> {
}
