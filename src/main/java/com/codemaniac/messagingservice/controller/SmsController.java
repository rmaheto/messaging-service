package com.codemaniac.messagingservice.controller;

import com.codemaniac.messagingservice.model.MessageDTO;
import com.codemaniac.messagingservice.model.SmsMessage;
import com.codemaniac.messagingservice.service.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SmsController {
    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestBody MessageDTO smsMessage) {
        smsService.queueSms(smsMessage);
        return ResponseEntity.ok("SMS sent successfully");
    }
}
