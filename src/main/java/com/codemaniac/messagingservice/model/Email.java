package com.codemaniac.messagingservice.model;

import lombok.Data;

import java.util.List;

@Data
public class Email extends IMessage {
    private List<String> to;
    private String subject;
    private String body;
}
