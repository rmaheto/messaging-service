package com.codemaniac.messagingservice;

import com.codemaniac.messagingservice.model.Email;
import com.codemaniac.messagingservice.model.MessageDTO;
import com.codemaniac.messagingservice.model.MessageType;
import com.codemaniac.messagingservice.model.QueuedMessage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestHelper {

    public static MessageDTO generateEmailMessageDTO(){
        MessageDTO email = new MessageDTO();
        email.setBody("Test Body");
        email.setSubject("Test Subject");
        List<String> receivers = Arrays.asList("receiver1@gmail.com", "receiver2@gmail.com");
        email.setReceivers(receivers);
        return email;
    }

    public static MessageDTO generateSmsMessageDTO(){
        MessageDTO sms = new MessageDTO();
        sms.setBody("Test Body");
        List<String> receivers = Arrays.asList("+1625257272", "+15153421234");
        sms.setReceivers(receivers);
        return sms;
    }

    public static Email generateTestEmail(){
        Email email = new Email();
        email.setReceiver("test@example.com");
        email.setSubject("Test Subject");
        email.setBody("Test Body");
        return  email;
    }

    public static QueuedMessage generateQueueMessage(){
        QueuedMessage message = new QueuedMessage();
        message.setBody("Test Body");
        message.setReceiver("receiver@gmail.com");
        message.setSubject("Test Subject");
        message.setType(MessageType.EMAIL);
        return message;
    }
}
