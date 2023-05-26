package com.codemaniac.messagingservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {
    private final PropertiesLoader propertiesLoader;
    @Value("${mail.host}")
    private String mailServer;
    @Value("${mail.port}")
    private int portNumber;
    @Value("${mail.protocol}")
    private String mailProtocol;
    @Value("${mail.properties.mail.smtp.auth}")
    private String mailAuth;
    @Value("${mail.debug}")
    private String debugMailServer;


    @Bean
    public JavaMailSender emailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailServer);
        mailSender.setPort(portNumber);

        mailSender.setUsername(propertiesLoader.getProperty("mail.username"));
        mailSender.setPassword(propertiesLoader.getProperty("mail.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailProtocol);
        props.put("mail.smtp.auth", mailAuth);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", debugMailServer);

        return mailSender;
    }
}
