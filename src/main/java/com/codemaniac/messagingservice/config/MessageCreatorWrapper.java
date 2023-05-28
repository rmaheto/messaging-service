package com.codemaniac.messagingservice.config;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;

@Component
public class MessageCreatorWrapper {
    public MessageCreator creator(PhoneNumber to, PhoneNumber from, String body) {
        return Message.creator(to, from, body);
    }
}
