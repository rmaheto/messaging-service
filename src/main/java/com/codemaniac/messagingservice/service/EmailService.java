package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.model.Email;
import com.codemaniac.messagingservice.model.MessageDTO;

public interface EmailService {
    public void sendEmail(Email email);
    public void queueEmail(MessageDTO email);
}
