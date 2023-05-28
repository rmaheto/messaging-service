package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.config.MessageCreatorWrapper;
import com.codemaniac.messagingservice.config.PropertiesLoader;
import com.codemaniac.messagingservice.config.TwilioConfig;
import com.codemaniac.messagingservice.exception.SmsSendingException;
import com.codemaniac.messagingservice.model.SmsMessage;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsServiceImpl implements SmsService{
    private final TwilioRestClient twilioClient;
    private final PropertiesLoader propertiesLoader;
    private final MessageCreatorWrapper messageCreatorWrapper;

    @Override
    public void sendSms(SmsMessage sms) {
        sms.getTo().stream().forEach(receiver -> {
            try{
                messageCreatorWrapper.creator(
                        new PhoneNumber(receiver),
                        new PhoneNumber(propertiesLoader.getProperty("twilio.phoneNumber")),
                        sms.getBody()
                ).create(twilioClient);
            }catch (ApiException e){
                log.error("Error sending SMS: {}", e.getMessage());
                throw new SmsSendingException("Failed to send SMS: " + e.getMessage(), e);
            }catch (Exception e) {
                // Handle other exceptions
                log.error("Error sending SMS: {}", e.getMessage());
                throw new SmsSendingException("Failed to send SMS: " + e.getMessage(), e);
            }
        });

    }
}
