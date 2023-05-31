package com.codemaniac.messagingservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Messaging Service API",
                version = "1.0",
                description = "This Messaging Service enables efficient dispatch of SMS and email " +
                        "messages for notifications, alerts, and more. Please note, it's designed " +
                        "exclusively for sending, not receiving messages",
                contact = @Contact(
                        name = "Raymond Aheto",
                        url = "http://www.codemaniacgh.com",
                        email = "support@codemaniacgh.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8090/api/v1", description = "Local server"),
        }
)
public class MessagingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessagingServiceApplication.class, args);
    }

}
