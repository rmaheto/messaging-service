package com.codemaniac.messagingservice.controller;

import com.codemaniac.messagingservice.model.Email;
import com.codemaniac.messagingservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final EmailService emailService;
    @PostMapping("/sendEmail")
    public ResponseEntity<Void> sendMessage(@RequestBody Email email){
                emailService.sendEmail(email);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
