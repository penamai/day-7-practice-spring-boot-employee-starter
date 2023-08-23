package com.thoughtworks.springbootemployee.exception;

public class NotValidEmployeeAgeException extends RuntimeException{
    public NotValidEmployeeAgeException() {
        super("Employee must be 18~65 years old");
    }
}
