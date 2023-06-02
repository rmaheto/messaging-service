package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.config.MessageCreatorWrapper;
import com.codemaniac.messagingservice.config.PropertiesLoader;
import com.codemaniac.messagingservice.exception.SmsSendingException;
import com.codemaniac.messagingservice.mapper.ObjectMapperUtil;
import com.codemaniac.messagingservice.model.MessageProperties;
import com.codemaniac.messagingservice.model.QueuedMessage;
import com.codemaniac.messagingservice.model.SmsMessage;
import com.twilio.http.TwilioRestClient;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsServiceImpl implements SmsService{
    private static final Logger messagesLogger = LoggerFactory.getLogger("messagesLogger");

    private final TwilioRestClient twilioClient;
    private final PropertiesLoader propertiesLoader;
    private final MessageCreatorWrapper messageCreatorWrapper;
    private final ObjectMapperUtil objectMapperUtil;
    private final QueueMessageServiceImpl queueMessageService;

    @Override
    public void sendSms(SmsMessage sms) {
            try{
                messageCreatorWrapper.creator(
                        new PhoneNumber(sms.getReceiver()),
                        new PhoneNumber(propertiesLoader.getProperty("twilio.phoneNumber")),
                        sms.getBody()
                ).create(twilioClient);
                messagesLogger.info("sms sent- receiver: {} body: {}",sms.getReceiver(), sms.getBody());
            } catch (Exception e){
                log.error("Error sending SMS: {}", e.getMessage());
                throw new SmsSendingException("Failed to send SMS: " + e.getMessage(), e);
            }

    }

    @Override
    public void queueSms(MessageProperties sms, String callingApplication) {
        sms.getReceivers().stream().forEach(receiver -> {
            QueuedMessage message = objectMapperUtil.mapSmsToMessage(receiver, sms.getBody(),callingApplication);
            queueMessageService.queueMessage(message);
        });
    }
}
