package com.thoughtworks.springbootemployee.controller;

import java.util.List;

public class Company {
    private final Long id;
    private final String name;
    private final List<Employee> companyEmployees;

    public Company(Long id, String name, List<Employee> companyEmployees) {
        this.id = id;
        this.name = name;
        this.companyEmployees = companyEmployees;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Employee> getCompanyEmployees() {
        return companyEmployees;
    }
}
