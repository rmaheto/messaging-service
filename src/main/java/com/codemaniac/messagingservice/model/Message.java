package com.codemaniac.messagingservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;
    private String receiver;
    private String subject;
    private String body;
    @Enumerated(EnumType.STRING)
    private MessageType type;
    @Embedded
    private Audit audit;
}
