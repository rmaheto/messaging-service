package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.TestHelper;
import com.codemaniac.messagingservice.model.QueuedMessage;
import com.codemaniac.messagingservice.repository.QueuedMessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QueueMessageServiceImplTest {
    @InjectMocks
    private QueueMessageServiceImpl queueMessageService;

    @Mock
    private QueuedMessageRepository messageRepository;

    @Captor
    ArgumentCaptor<QueuedMessage> captor;

    @Test
    public void testQueueMessage() {
        // Setup
        QueuedMessage message = TestHelper.generateQueueMessage();

        // Execute
        queueMessageService.queueMessage(message);

        // Verify
        verify(messageRepository).save(captor.capture());
        QueuedMessage capturedMessage = captor.getValue();

        // Assert
        assertEquals("Pending", capturedMessage.getStatus());
        assertEquals(message.getBody(), capturedMessage.getBody());
        assertEquals(message.getReceiver(), capturedMessage.getReceiver());
        assertEquals(message.getSubject(), capturedMessage.getSubject());
        assertEquals(message.getType(), capturedMessage.getType());
    }
}