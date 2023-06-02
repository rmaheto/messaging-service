package com.codemaniac.messagingservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Email extends IMessage {
    private String subject;
}
