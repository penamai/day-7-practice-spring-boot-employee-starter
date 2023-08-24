package com.thoughtworks.springbootemployee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotValidEmployeeAgeException extends RuntimeException{
    public NotValidEmployeeAgeException() {
        super("Employee must be 18~65 years old");
    }
}
