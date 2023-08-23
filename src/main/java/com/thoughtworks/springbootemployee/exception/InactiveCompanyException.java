package com.thoughtworks.springbootemployee.exception;

public class InactiveCompanyException extends RuntimeException{
    public InactiveCompanyException() {
        super("Company is inactive");
    }
}
