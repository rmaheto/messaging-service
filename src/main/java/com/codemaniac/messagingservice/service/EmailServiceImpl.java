package com.codemaniac.messagingservice.service;


import com.codemaniac.messagingservice.exception.EmailServerAuthenticationException;
import com.codemaniac.messagingservice.mapper.ObjectMapperUtil;
import com.codemaniac.messagingservice.model.Email;
import com.codemaniac.messagingservice.model.MessageProperties;
import com.codemaniac.messagingservice.model.QueuedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final Logger messagesLogger = LoggerFactory.getLogger("messagesLogger");

    private final JavaMailSender emailSender;
    private final ObjectMapperUtil objectMapperUtil;
    private final QueueMessageServiceImpl queueMessageService;

    @Override
    public void sendEmail(Email email) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("no-reply@gmail.com");
            message.setTo(email.getReceiver());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            try {
                emailSender.send(message);
                messagesLogger.info("email sent- receiver: {} subject: {} body: {}",email.getReceiver(),email.getSubject(),
                        email.getBody());
            } catch (Exception e) {
                log.warn("Error sending email: {}", e.getCause());
                if (e instanceof MailAuthenticationException) {
                    log.error("Authentication failed: Bad Email Server Credentials: {}",e.getMessage());
                    throw new EmailServerAuthenticationException("Authentication failed: Bad Email Server Credentials");
                }
                throw e; // re-throw the exception
            }
    }

    @Override
    public void queueEmail(MessageProperties email, String callingApplication) {
        email.getReceivers().stream().forEach(receiver -> {
            QueuedMessage msg = objectMapperUtil.mapEmailToMessage(receiver, email.getSubject(), email.getBody(),callingApplication);
            queueMessageService.queueMessage(msg);
        });
    }

}
