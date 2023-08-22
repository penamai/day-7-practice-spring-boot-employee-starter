package com.thoughtworks.springbootemployee.controller.exception;

public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException() {
        super("Company not found.");
    }
}
