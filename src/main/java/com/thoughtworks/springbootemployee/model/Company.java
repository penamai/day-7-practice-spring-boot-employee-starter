package com.thoughtworks.springbootemployee.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class Company {
    private Long id;
    private final String name;

    @JsonCreator
    public Company(String name){
        this.id = null;
        this.name = name;
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

}
