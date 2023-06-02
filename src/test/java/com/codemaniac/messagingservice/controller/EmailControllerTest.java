package com.codemaniac.messagingservice.controller;

import com.codemaniac.messagingservice.TestHelper;
import com.codemaniac.messagingservice.model.MessageProperties;
import com.codemaniac.messagingservice.service.EmailService;
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
public class EmailControllerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController underTest;

    @Test
    public void sendMessage() {
        // Prepare test data
        MessageProperties message = new MessageProperties();
        message.setReceivers(Collections.singletonList("test@example.com"));
        message.setBody("Test email body");

        // Create an ArgumentCaptor
        ArgumentCaptor<MessageProperties> emailCaptor = ArgumentCaptor.forClass(MessageProperties.class);
        // Call the method under test
        ResponseEntity<Void> response = underTest.sendMessage(message,TestHelper.TEST_APP);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the service was called
        verify(emailService, times(1)).queueEmail(emailCaptor.capture(),eq(TestHelper.TEST_APP));
        // Assert the argument
        MessageProperties capturedEmail = emailCaptor.getValue();
        assertEquals("test@example.com", capturedEmail.getReceivers().get(0));
        assertEquals("Test email body", capturedEmail.getBody());
    }
}