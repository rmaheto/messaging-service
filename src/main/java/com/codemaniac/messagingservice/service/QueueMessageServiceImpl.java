package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.model.QueuedMessage;
import com.codemaniac.messagingservice.repository.QueuedMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueueMessageServiceImpl {
    private final QueuedMessageRepository messageRepository;
    @Transactional
    public void queueMessage(QueuedMessage message){
        message.setStatus("Pending");
        messageRepository.save(message);
    }
}
