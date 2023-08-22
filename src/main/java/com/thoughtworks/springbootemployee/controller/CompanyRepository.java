package com.thoughtworks.springbootemployee.controller;

import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();

    static {
        companies.add(new Company(1L, "firstCompany"));
        companies.add(new Company(2L, "middleCompany"));
        companies.add(new Company(3L, "lastCompany"));
    }

    public List<Company> listAllCompanies() {
        return companies;
    }
}
