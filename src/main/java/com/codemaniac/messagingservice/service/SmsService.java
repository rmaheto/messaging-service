package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.model.SmsMessage;

public interface SmsService {
    public void sendSms(SmsMessage smsMessage);
}
