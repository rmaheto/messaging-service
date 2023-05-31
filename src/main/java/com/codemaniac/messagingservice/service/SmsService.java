package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.model.MessageDTO;
import com.codemaniac.messagingservice.model.SmsMessage;

public interface SmsService {
    public void sendSms(SmsMessage smsMessage);
    public void queueSms(MessageDTO smsMessage, String callingApplication);
}
