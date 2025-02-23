package com.thoughtworks.springbootemployee.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Company {
    private Long id;
    private String name;
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
        this.isActive = TRUE;
    }

    public Company(Long id, Company company) {
        this(company.getName());
        this.id = id;
        this.isActive = TRUE;
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

    public void setToInactive() {
        this.isActive = FALSE;
    }

    public void update(Company updatedCompanyInfo){
        this.name = updatedCompanyInfo.getName();
    }
}
