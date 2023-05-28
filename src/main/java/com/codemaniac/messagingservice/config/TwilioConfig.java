package com.codemaniac.messagingservice.config;

import com.twilio.http.TwilioRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class TwilioConfig {
    private final PropertiesLoader propertiesLoader;
    private String accountSid;
    private String authToken;

    @Bean
    public TwilioRestClient twilioRestClient() {
        accountSid=propertiesLoader.getProperty("twilio.accountSid");
        authToken=propertiesLoader.getProperty("twilio.authToken");
        return new TwilioRestClient.Builder(accountSid, authToken).build();
    }
}
