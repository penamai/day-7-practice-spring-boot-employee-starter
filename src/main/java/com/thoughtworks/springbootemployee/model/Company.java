package com.thoughtworks.springbootemployee.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Company {
    private Long id;
    private final String name;
    private Boolean isActive;

    @JsonCreator
    public Company(String name){
        this.id = null;
        this.name = name;
        this.isActive = TRUE;
    }

    public Company(Long id, String name) {
        this(name);
        this.id = id;
    }

    public Company(Long id, Company company) {
        this(company.getName());
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean isActive(){
        return isActive;
    }

    public void setAsInactive() {
        this.isActive = FALSE;
    }
}
