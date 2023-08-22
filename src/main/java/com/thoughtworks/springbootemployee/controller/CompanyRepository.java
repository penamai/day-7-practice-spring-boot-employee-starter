package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.controller.exception.CompanyNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();

    static {
        companies.add(new Company(1L, "firstCompany", new EmployeeRepository().getAllEmployees()));
        companies.add(new Company(2L, "middleCompany", new EmployeeRepository().getAllEmployees()));
        companies.add(new Company(3L, "lastCompany", new EmployeeRepository().getAllEmployees()));
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

    public List<Employee> getCompanyListOfEmployees(Long id) {
        return getCompanyById(id).getCompanyEmployees();
    }

    public List<Company> listCompaniesByPage(Integer pageNumber, Integer pageSize) {
        int fromIndex = pageSize*(pageNumber - 1);
        int toIndex = fromIndex + pageSize;
        if(toIndex > companies.size())
            toIndex = companies.size();
        return companies.subList(fromIndex, toIndex);
    }
}
