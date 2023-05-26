package com.codemaniac.messagingservice.exception;

public class EmailServerAuthenticationException extends RuntimeException {

    public EmailServerAuthenticationException(String message){
        super(message);
    }
}
