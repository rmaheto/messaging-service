package com.codemaniac.messagingservice.controller;

import com.codemaniac.messagingservice.model.Email;
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
        Email email = new Email();
        email.setTo(Collections.singletonList("test@example.com"));
        email.setBody("Test email body");

        // Create an ArgumentCaptor
        ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);
        // Call the method under test
        ResponseEntity<Void> response = underTest.sendMessage(email);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the service was called
        verify(emailService, times(1)).sendEmail(emailCaptor.capture());
        // Assert the argument
        Email capturedEmail = emailCaptor.getValue();
        assertEquals("test@example.com", capturedEmail.getTo().get(0));
        assertEquals("Test email body", capturedEmail.getBody());
    }
}