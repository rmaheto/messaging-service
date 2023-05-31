package com.codemaniac.messagingservice.service;

import com.codemaniac.messagingservice.mapper.ObjectMapperUtil;
import com.codemaniac.messagingservice.model.MessageType;
import com.codemaniac.messagingservice.model.QueuedMessage;
import com.codemaniac.messagingservice.model.SentMessage;
import com.codemaniac.messagingservice.repository.QueuedMessageRepository;
import com.codemaniac.messagingservice.repository.SentMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final QueuedMessageRepository messageRepository;
    private final SentMessageRepository sentMessageRepository;
    private final ObjectMapperUtil objectMapperUtil;
    private final SmsService smsService;
    private final EmailService emailService;




    @Scheduled(fixedRate = 60000) // run every 60 seconds
    public void processMessages() {
        List<QueuedMessage> messages = messageRepository.findAllByStatusOrderByCreateTimestampAsc("Pending");

        for(QueuedMessage message: messages) {
            try {
                if(message.getType().equals(MessageType.SMS)) {
                    smsService.sendSms(objectMapperUtil.mapMessageToSms(message));
                } else if (message.getType().equals(MessageType.EMAIL)) {
                    emailService.sendEmail(objectMapperUtil.mapMessageToEmail(message));
                }

                // Message sent successfully, create a SentMessage record
                SentMessage sentMessage = new SentMessage();
                BeanUtils.copyProperties(message, sentMessage);
                sentMessage.setSentAt(LocalDateTime.now());

                sentMessageRepository.save(sentMessage);
                messageRepository.delete(message);

            } catch (Exception e) {
                message.setStatus("Error");
                message.setRetryCount(message.getRetryCount() + 1);
                messageRepository.save(message);
            }
        }
    }
}
