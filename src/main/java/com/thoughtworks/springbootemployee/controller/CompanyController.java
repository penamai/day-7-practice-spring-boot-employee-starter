package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}/employees")
    public List<Employee> getCompanyListOfEmployees(@PathVariable Long id){
        return companyRepository.getCompanyListOfEmployees(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> listCompaniesByPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return companyRepository.listCompaniesByPage(pageNumber, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addACompany(@RequestBody  Company company){
        companyRepository.addACompany(company);
    }

    @PutMapping("/{id}")
    public Company updateCompanyNameById(@PathVariable Long id, @RequestBody Company updatedCompanyInfo){
        return companyRepository.updateCompanyById(id, updatedCompanyInfo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable Long id){
        companyRepository.deleteCompanyById(id);
    }
}
