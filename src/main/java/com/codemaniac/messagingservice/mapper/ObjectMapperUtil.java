package com.codemaniac.messagingservice.mapper;

import com.codemaniac.messagingservice.model.*;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperUtil {

    public QueuedMessage mapEmailToMessage(String receiver, String subject, String body,String callingApplication){
        QueuedMessage message = new QueuedMessage();
        message.setReceiver(receiver);
        message.setSubject(subject);
        message.setType(MessageType.EMAIL);
        message.setBody(body);
        message.setAudit(new Audit(callingApplication,callingApplication));
        return message;
    }

    public Email mapMessageToEmail(QueuedMessage message){
        Email email = new Email();
        email.setReceiver(message.getReceiver());
        email.setSubject(message.getSubject());
        email.setBody(message.getBody());
        return email;
    }

    public QueuedMessage mapSmsToMessage(String receiver,  String body,String callingApplication){
        QueuedMessage message = new QueuedMessage();
        message.setReceiver(receiver);
        message.setBody(body);
        message.setType(MessageType.SMS);
        message.setAudit(new Audit(callingApplication,callingApplication));
        return message;
    }

    public SmsMessage mapMessageToSms(QueuedMessage message){
        SmsMessage sms = new SmsMessage();
        sms.setReceiver(message.getReceiver());
        sms.setBody(message.getBody());
        return sms;
    }
}
