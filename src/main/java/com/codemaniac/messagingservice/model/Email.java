package com.codemaniac.messagingservice.model;

import lombok.Data;

import java.util.List;

@Data
public class Email extends IMessage {
    private String subject;
}
