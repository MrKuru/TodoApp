package com.hb.exception.exceptions;

public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(String message){
        super(message);
    }
}
