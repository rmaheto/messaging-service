package com.codemaniac.messagingservice.model;

import lombok.Data;

import java.util.List;
@Data
public abstract class IMessage {
    private String receiver;
    private String body;
}
