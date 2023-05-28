package com.codemaniac.messagingservice.exception;


public class SmsSendingException extends RuntimeException {
    public SmsSendingException(String s, Exception e) {
        super(s, e);
    }
}
