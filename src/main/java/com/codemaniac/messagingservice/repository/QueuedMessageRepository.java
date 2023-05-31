package com.codemaniac.messagingservice.repository;

import com.codemaniac.messagingservice.model.QueuedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueuedMessageRepository extends JpaRepository<QueuedMessage, Long> {
    @Query("SELECT q FROM QueuedMessage q WHERE q.status = :status ORDER BY q.audit.createTimestamp ASC")
    public List<QueuedMessage> findAllByStatusOrderByCreateTimestampAsc(@Param("status") String status);
}
