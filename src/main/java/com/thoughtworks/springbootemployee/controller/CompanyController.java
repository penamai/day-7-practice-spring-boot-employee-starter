package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/companies")
public class CompanyController {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyController(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Company> listAllCompanies(){
        return companyRepository.listAllCompanies();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Long id){
        return companyRepository.getCompanyById(id);
    }
}
