package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.TestHelper;
import com.codemaniac.messagingservice.config.MessageCreatorWrapper;
import com.codemaniac.messagingservice.config.PropertiesLoader;
import com.codemaniac.messagingservice.exception.SmsSendingException;
import com.codemaniac.messagingservice.mapper.ObjectMapperUtil;
import com.codemaniac.messagingservice.model.MessageDTO;
import com.codemaniac.messagingservice.model.QueuedMessage;
import com.codemaniac.messagingservice.model.SmsMessage;
import com.twilio.exception.ApiException;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

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
    @Mock
    private QueueMessageServiceImpl queueMessageService;
    @Mock
    private ObjectMapperUtil objectMapperUtil;
    @Captor
    ArgumentCaptor<QueuedMessage> captor;

    private SmsMessage sms;
    private MessageDTO messageDTO;

    @InjectMocks
    private SmsServiceImpl smsService;

    @Before
    public void setup() {

        // Prepare test data
        sms = new SmsMessage();
        sms.setReceiver("1234567890");
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
        assertEquals(sms.getReceiver(), toCaptor.getValue().getEndpoint());
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

    @Test
    public void queueEmail_Test() {
        // Setup
        MessageDTO sms = TestHelper.generateSmsMessageDTO();

        // Mocks
        QueuedMessage mockedMessage = new QueuedMessage();
        when(objectMapperUtil.mapSmsToMessage(anyString(), anyString(),anyString())).thenReturn(mockedMessage);

        // Execute
        smsService.queueSms(sms,TestHelper.TEST_APP);

        // Verify
        verify(queueMessageService, times(sms.getReceivers().size())).queueMessage(captor.capture());
        List<QueuedMessage> capturedMessages = captor.getAllValues();

        // Assert
        assertEquals(sms.getReceivers().size(), capturedMessages.size());
        for (QueuedMessage message : capturedMessages) {
            assertEquals(mockedMessage, message);
        }
    }
}