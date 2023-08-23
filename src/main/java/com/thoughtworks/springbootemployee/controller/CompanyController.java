package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/companies")
public class CompanyController {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public CompanyController(CompanyRepository companyRepository, EmployeeRepository employeeRepository){
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
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
        return companyRepository.getCompanyListOfEmployees(id, employeeRepository);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> listCompaniesByPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return companyRepository.listCompaniesByPage(pageNumber, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addACompany(@RequestBody  Company company){
        return companyRepository.addACompany(company);
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
