package com.codemaniac.messagingservice.exception;

public class FileLoadException extends RuntimeException{

  public FileLoadException(String msg, Throwable cause){
        super(msg,cause);
    }
}
