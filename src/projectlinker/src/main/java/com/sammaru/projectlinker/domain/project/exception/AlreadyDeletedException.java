package com.sammaru.projectlinker.domain.project.exception;

public class AlreadyDeletedException extends RuntimeException{
    public AlreadyDeletedException(String message){
        super(message);
    }
}
