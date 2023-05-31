package com.codemaniac.messagingservice.controller;

import com.codemaniac.messagingservice.TestHelper;
import com.codemaniac.messagingservice.model.MessageDTO;
import com.codemaniac.messagingservice.model.SmsMessage;
import com.codemaniac.messagingservice.service.SmsService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SmsControllerTest {

    @Mock
    private SmsService smsService;
    @InjectMocks
    private SmsController underTest;

    @Test
    public void sendSms() {

        MessageDTO sms = new MessageDTO();
        sms.setReceivers(Collections.singletonList("+1525215722"));
        sms.setBody("Hello, World!");

        // Create an ArgumentCaptor
        ArgumentCaptor<MessageDTO> smsCaptor = ArgumentCaptor.forClass(MessageDTO.class);
        // Call the method under test
        ResponseEntity<String > response = underTest.sendSms(sms, TestHelper.TEST_APP);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the service was called
        verify(smsService, times(1)).queueSms(smsCaptor.capture(),eq(TestHelper.TEST_APP));
        // Assert the argument
        MessageDTO capturedSms = smsCaptor.getValue();
        assertEquals("+1525215722", capturedSms.getReceivers().get(0));
        assertEquals("Hello, World!", capturedSms.getBody());
    }
}