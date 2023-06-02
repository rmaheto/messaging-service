package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.model.MessageProperties;
import com.codemaniac.messagingservice.model.SmsMessage;

public interface SmsService {
    void sendSms(SmsMessage smsMessage);
    void queueSms(MessageProperties smsMessage, String callingApplication);
}
