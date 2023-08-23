package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();
    public static final long DEFAULT_INCREMENT = 1L;

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

    public List<Company> listCompaniesByPage(Integer pageNumber, Integer pageSize) {
        int fromIndex = pageSize * (pageNumber - 1);
        int toIndex = fromIndex + pageSize;
        if (toIndex > companies.size())
            toIndex = companies.size();
        return companies.subList(fromIndex, toIndex);
    }

    public Company addACompany(Company company) {
        Company newCompany = new Company(generateNextId(), company);
        companies.add(newCompany);
        return newCompany;
    }

    private Long generateNextId() {
        if (companies.isEmpty()) {
            return DEFAULT_INCREMENT;
        }
        Optional<Company> companyWithMaxId = companies.stream()
                .max(Comparator.comparingLong(Company::getId));
        return companyWithMaxId.get().getId() + DEFAULT_INCREMENT;
    }

    public Company updateCompanyById(Long id, Company updatedCompanyInfo) {
        Company companyToBeUpdated = getCompanyById(id);
        Company updatedCompany = new Company(id, updatedCompanyInfo.getName());

        companies.set(companies.indexOf(companyToBeUpdated), updatedCompany);
        return updatedCompany;
    }

    public void cleanup() {
        companies.clear();
    }

    public void setToInactive(Long id) {
        Company companyToDelete = getCompanyById(id);
        companyToDelete.setToInactive();
    }
}
