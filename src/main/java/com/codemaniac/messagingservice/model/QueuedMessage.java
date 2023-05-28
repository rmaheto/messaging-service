package com.codemaniac.messagingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "queued_messages")
@Data
public class QueuedMessage extends Message{
    private String status;
    private int retryCount;
}
