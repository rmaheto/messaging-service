package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.TestHelper;
import com.codemaniac.messagingservice.exception.EmailServerAuthenticationException;
import com.codemaniac.messagingservice.mapper.ObjectMapperUtil;
import com.codemaniac.messagingservice.model.Email;
import com.codemaniac.messagingservice.model.MessageProperties;
import com.codemaniac.messagingservice.model.QueuedMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {
    @Mock
    private JavaMailSender emailSender;
    @Mock
    private QueueMessageServiceImpl queueMessageService;
    @Mock
    private ObjectMapperUtil objectMapperUtil;
    @Captor
    ArgumentCaptor<QueuedMessage> captor;
    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    public void sendEmail_Success() {

        Email email = TestHelper.generateTestEmail();

        // Perform the test
        emailService.sendEmail(email);

        // Verify that the email sender's send method was called once
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test(expected = EmailServerAuthenticationException.class)
    public void sendEmail_AuthenticationException_ThrowsException() {
        // Prepare test data
        Email email = TestHelper.generateTestEmail();

        // Set up the email sender to throw a MailAuthenticationException
        doThrow(new MailAuthenticationException("Authentication failed"))
                .when(emailSender).send(any(SimpleMailMessage.class));

        // Perform the test, expecting an EmailServerAuthenticationException
        emailService.sendEmail(email);
    }

    @Test
    public void queueEmail_Test() {
        // Setup
        MessageProperties email = TestHelper.generateEmailMessageDTO();

        // Mocks
        QueuedMessage mockedMessage = new QueuedMessage();
        when(objectMapperUtil.mapEmailToMessage(anyString(), anyString(), anyString(),anyString())).thenReturn(mockedMessage);

        // Execute
        emailService.queueEmail(email,"SYSTEM");

        // Verify
        verify(queueMessageService, times(email.getReceivers().size())).queueMessage(captor.capture());
        List<QueuedMessage> capturedMessages = captor.getAllValues();

        // Assert
        assertEquals(email.getReceivers().size(), capturedMessages.size());
        for (QueuedMessage message : capturedMessages) {
            assertEquals(mockedMessage, message);
        }
    }
}