package com.codemaniac.messagingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "sent_messages")
@Data
public class SentMessage extends Message{
    private LocalDateTime sentAt;
}
