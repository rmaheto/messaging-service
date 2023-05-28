package com.codemaniac.messagingservice.repository;

import com.codemaniac.messagingservice.model.QueuedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueuedMessageRepository extends JpaRepository<QueuedMessage, Long> {
    public List<QueuedMessage> findAllByStatus(String status);
}
