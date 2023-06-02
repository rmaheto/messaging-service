package com.codemaniac.messagingservice.controller;

import com.codemaniac.messagingservice.model.MessageProperties;
import com.codemaniac.messagingservice.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody MessageProperties email,
                                            @RequestHeader(value = "Calling-Application")
                                                    String callingApplication){
                emailService.queueEmail(email, callingApplication);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
