package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.mapper.ObjectMapperUtil;
import com.codemaniac.messagingservice.model.*;
import com.codemaniac.messagingservice.repository.QueuedMessageRepository;
import com.codemaniac.messagingservice.repository.SentMessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    @Mock
    private QueuedMessageRepository messageRepository;

    @Mock
    private SentMessageRepository sentMessageRepository;

    @Mock
    private ObjectMapperUtil objectMapperUtil;

    @Mock
    private SmsService smsService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private MessageService messageService;

    @Test
    public void testProcessMessagesForEmail() {
        // Setup
        QueuedMessage mockMessage = new QueuedMessage();
        mockMessage.setType(MessageType.EMAIL);
        mockMessage.setStatus("Pending");
        List<QueuedMessage> mockMessageList = Arrays.asList(mockMessage);

        when(messageRepository.findAllByStatus("Pending")).thenReturn(mockMessageList);

        Email mockEmail = new Email();
        when(objectMapperUtil.mapMessageToEmail(mockMessage)).thenReturn(mockEmail);

        // Exercise
        messageService.processMessages();

        // Verify
        verify(emailService, times(1)).sendEmail(mockEmail);
        verify(messageRepository, times(1)).delete(mockMessage);

        // Assertions
        ArgumentCaptor<SentMessage> captor = ArgumentCaptor.forClass(SentMessage.class);
        verify(sentMessageRepository).save(captor.capture());
        SentMessage sentMessage = captor.getValue();

        assertEquals(mockMessage.getReceiver(), sentMessage.getReceiver());
        assertEquals(mockMessage.getSubject(), sentMessage.getSubject());
        // ... continue to assert the rest of the properties
    }

    @Test
    public void testProcessMessagesForSms() {
        // Setup
        QueuedMessage mockMessage = new QueuedMessage();
        mockMessage.setType(MessageType.SMS);
        mockMessage.setStatus("Pending");
        List<QueuedMessage> mockMessageList = Arrays.asList(mockMessage);

        when(messageRepository.findAllByStatus("Pending")).thenReturn(mockMessageList);

        SmsMessage mockSms = new SmsMessage();
        when(objectMapperUtil.mapMessageToSms(mockMessage)).thenReturn(mockSms);

        // Exercise
        messageService.processMessages();

        // Verify
        verify(smsService, times(1)).sendSms(mockSms);
        verify(messageRepository, times(1)).delete(mockMessage);

        // Assertions
        ArgumentCaptor<SentMessage> captor = ArgumentCaptor.forClass(SentMessage.class);
        verify(sentMessageRepository).save(captor.capture());
        SentMessage sentMessage = captor.getValue();

        assertEquals(mockMessage.getReceiver(), sentMessage.getReceiver());
        assertEquals(mockMessage.getSubject(), sentMessage.getSubject());
        // ... continue to assert the rest of the properties
    }

    @Test
    public void testProcessMessagesWhenExceptionOccurs() {
        // Setup
        QueuedMessage mockMessage = new QueuedMessage();
        mockMessage.setType(MessageType.EMAIL);
        mockMessage.setStatus("Pending");
        mockMessage.setRetryCount(0);
        List<QueuedMessage> mockMessageList = Arrays.asList(mockMessage);

        when(messageRepository.findAllByStatus("Pending")).thenReturn(mockMessageList);

        Email mockEmail = new Email();
        when(objectMapperUtil.mapMessageToEmail(mockMessage)).thenReturn(mockEmail);

        // Mock exception when sending email
        doThrow(new RuntimeException()).when(emailService).sendEmail(mockEmail);

        // Exercise
        messageService.processMessages();

        // Verify
        verify(emailService, times(1)).sendEmail(mockEmail);

        // Assert that the message's status is updated and retry count is increased
        ArgumentCaptor<QueuedMessage> captor = ArgumentCaptor.forClass(QueuedMessage.class);
        verify(messageRepository).save(captor.capture());
        QueuedMessage savedMessage = captor.getValue();

        assertEquals("Error", savedMessage.getStatus());
        assertEquals(1, savedMessage.getRetryCount());
    }
}