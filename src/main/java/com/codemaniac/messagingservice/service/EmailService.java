package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.model.Email;
import com.codemaniac.messagingservice.model.MessageProperties;

public interface EmailService {
    void sendEmail(Email email);
    void queueEmail(MessageProperties email, String callingApplication);
}
