package com.sammaru.projectlinker.domain.project.exception;

public class UnAuthorizationException extends RuntimeException{
    public UnAuthorizationException(String message){
        super(message);
    }
}
