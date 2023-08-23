package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.controller.exception.CompanyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
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

    public Company getCompanyById(Long id) {
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    public List<Employee> getCompanyListOfEmployees(Long companyId, EmployeeRepository employeeRepository) {
        return employeeRepository.getEmployeesByCompanyId(companyId);
    }

    public List<Company> listCompaniesByPage(Integer pageNumber, Integer pageSize) {
        int fromIndex = pageSize*(pageNumber - 1);
        int toIndex = fromIndex + pageSize;
        if(toIndex > companies.size())
            toIndex = companies.size();
        return companies.subList(fromIndex, toIndex);
    }

    public void addACompany(Company company) {
        companies.add(company);
    }

    public Company updateCompanyById(Long id, Company updatedCompanyInfo) {
        Company companyToBeUpdated = getCompanyById(id);
        Company updatedCompany = new Company(id, updatedCompanyInfo.getName());

        companies.set(companies.indexOf(companyToBeUpdated), updatedCompany);
        return updatedCompany;
    }

    public void deleteCompanyById(Long id) {
        companies.remove(getCompanyById(id));
    }
}
