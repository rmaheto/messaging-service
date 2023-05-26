package com.codemaniac.messagingservice.model;

import lombok.Data;

import java.util.List;

@Data
public class Email {
    private List<String> to;
    private String subject;
    private String body;
}
