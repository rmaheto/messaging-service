package com.codemaniac.messagingservice.model;

import lombok.Data;

import java.util.List;
@Data
public class MessageProperties {
    private List<String> receivers;
    private String subject;
    private String body;
}
