package com.codemaniac.messagingservice.service;


import com.codemaniac.messagingservice.exception.EmailServerAuthenticationException;
import com.codemaniac.messagingservice.model.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;

    @Override
    public void sendEmail(Email email) {
        email.getTo().stream().forEach(receiver -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("no-reply@gmail.com");
            message.setTo(receiver);
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            try {
                emailSender.send(message);
            } catch (Exception e) {
                log.warn("Error sending email: {}", e.getCause());
                if (e instanceof MailAuthenticationException) {
                    throw new EmailServerAuthenticationException("Authentication failed: Bad Email Server Credentials");
                }
            }
        });
    }

    @Autowired
    public void setEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }
}
