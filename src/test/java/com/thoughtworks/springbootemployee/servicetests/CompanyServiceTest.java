package com.thoughtworks.springbootemployee.servicetests;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompanyServiceTest {
    @Autowired
    private CompanyService companyService;

    private CompanyRepository mockedCompanyRepository;

    @BeforeEach
    void setup() {
        mockedCompanyRepository = mock(CompanyRepository.class);
        companyService = new CompanyService(mockedCompanyRepository);
    }

    @Test
    void should_return_created_company_when_create_company_given_company() {
        Company company = new Company("Company name");
        Company savedCompany = new Company(1L, "Company name");
        when(mockedCompanyRepository.addACompany(company)).thenReturn(savedCompany);

        Company createdCompany = companyService.create(company);

        Assertions.assertEquals(1L, createdCompany.getId());
        Assertions.assertEquals("Company name", createdCompany.getName());
    }
}
