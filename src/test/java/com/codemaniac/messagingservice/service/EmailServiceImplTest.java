package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.exception.EmailServerAuthenticationException;
import com.codemaniac.messagingservice.model.Email;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {
    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    public void sendEmail_Success() {

        Email email = new Email();
        email.setTo(Collections.singletonList("test@example.com"));
        email.setSubject("Test Subject");
        email.setBody("Test Body");

        // Perform the test
        emailService.sendEmail(email);

        // Verify that the email sender's send method was called once
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test(expected = EmailServerAuthenticationException.class)
    public void sendEmail_AuthenticationException_ThrowsException() {
        // Prepare test data
        Email email = new Email();
        email.setTo(Collections.singletonList("test@example.com"));
        email.setSubject("Test Subject");
        email.setBody("Test Body");

        // Set up the email sender to throw a MailAuthenticationException
        doThrow(new MailAuthenticationException("Authentication failed"))
                .when(emailSender).send(any(SimpleMailMessage.class));

        // Perform the test, expecting an EmailServerAuthenticationException
        emailService.sendEmail(email);
    }
}