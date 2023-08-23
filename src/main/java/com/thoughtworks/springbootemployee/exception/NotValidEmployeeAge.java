package com.thoughtworks.springbootemployee.exception;

public class NotValidEmployeeAge extends RuntimeException{
    public NotValidEmployeeAge() {
        super("Employee must be 18~65 years old");
    }
}
