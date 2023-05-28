package com.codemaniac.messagingservice.model;

import lombok.Data;

import java.util.List;
@Data
public abstract class IMessage {
    private List<String> to;
    private String body;
}
