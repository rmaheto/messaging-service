package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.config.MessageCreatorWrapper;
import com.codemaniac.messagingservice.config.PropertiesLoader;
import com.codemaniac.messagingservice.exception.SmsSendingException;
import com.codemaniac.messagingservice.model.SmsMessage;
import com.twilio.exception.ApiException;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SmsServiceImplTest {

    private static final String TWILIO_NUMBER = "+182822727723";
    @Mock
    private TwilioRestClient twilioClient;

    @Mock
    private Message message;
    @Mock
    private MessageCreatorWrapper messageCreatorWrapper;

    @Mock
    private MessageCreator messageCreator;

    @Mock
    private PropertiesLoader propertiesLoader;

    private SmsMessage sms;

    @InjectMocks
    private SmsServiceImpl smsService;

    @Before
    public void setup() {

        // Prepare test data
        sms = new SmsMessage();
        sms.setTo(Collections.singletonList("1234567890"));
        sms.setBody("Test SMS");

    }

    @Test
    public void sendSms_Success() {

        when(propertiesLoader.getProperty("twilio.phoneNumber")).thenReturn(TWILIO_NUMBER);
        when(messageCreatorWrapper.creator(any(PhoneNumber.class),
                any(PhoneNumber.class), anyString())).thenReturn(messageCreator);
        when(messageCreator.create(twilioClient)).thenReturn(null);

        ArgumentCaptor<PhoneNumber> toCaptor = ArgumentCaptor.forClass(PhoneNumber.class);
        ArgumentCaptor<PhoneNumber> fromCaptor = ArgumentCaptor.forClass(PhoneNumber.class);
        ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

        smsService.sendSms(sms);

        verify(messageCreator).create(twilioClient);

        // Capture and verify the arguments
        verify(messageCreatorWrapper).creator(toCaptor.capture(), fromCaptor.capture(), bodyCaptor.capture());
        assertEquals(sms.getTo().get(0), toCaptor.getValue().getEndpoint());
        assertEquals(TWILIO_NUMBER, fromCaptor.getValue().getEndpoint());
        assertEquals(sms.getBody(), bodyCaptor.getValue());
    }

    @Test(expected = SmsSendingException.class)
    public void sendSms_ApiException() {
        when(propertiesLoader.getProperty("twilio.phoneNumber")).thenReturn(TWILIO_NUMBER);
        when(messageCreatorWrapper.creator(any(PhoneNumber.class), any(PhoneNumber.class), anyString())).thenThrow(ApiException.class);

        smsService.sendSms(sms);
    }

    @Test(expected = SmsSendingException.class)
    public void sendSms_GenericException() {
        when(propertiesLoader.getProperty("twilio.phoneNumber")).thenReturn(TWILIO_NUMBER);
        when(messageCreatorWrapper.creator(any(PhoneNumber.class), any(PhoneNumber.class), anyString())).thenThrow(RuntimeException.class);

        smsService.sendSms(sms);
    }
}