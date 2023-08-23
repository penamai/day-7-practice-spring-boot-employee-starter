package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.InactiveCompanyException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public Company create(Company company) {
        return companyRepository.addACompany(company);
    }

    public void delete(Long id) {
        companyRepository.setToInactive(id);
    }

    public Company update(Long id, Company updatedCompanyInfo) {
        Company companyToUpdate = companyRepository.getCompanyById(id);
        if(!companyToUpdate.isActive())
            throw new InactiveCompanyException();
        companyToUpdate.update(updatedCompanyInfo);

        return companyToUpdate;
    }

    public List<Company> findAll() {
        return companyRepository.listAllCompanies();
    }

    public Company findById(Long id) {
        return companyRepository.getCompanyById(id);
    }

    public List<Company> findByPage(Integer pageNumber, Integer pageSize) {
        return companyRepository.listCompaniesByPage(pageNumber, pageSize);
    }
}
