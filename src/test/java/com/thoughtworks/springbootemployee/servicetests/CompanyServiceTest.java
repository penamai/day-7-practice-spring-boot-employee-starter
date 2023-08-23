package com.thoughtworks.springbootemployee.servicetests;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
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

        assertEquals(1L, createdCompany.getId());
        assertEquals("Company name", createdCompany.getName());
    }

    @Test
    void should_set_active_false_when_delete_given_company_id() {
        Company company = new Company(1L, "Couchpany");

        when(mockedCompanyRepository.getCompanyById(company.getId())).thenReturn(company);

        companyService.delete(company.getId());

        mockedCompanyRepository.updateCompanyById(eq(company.getId()), argThat(tempCompany -> {
            assertFalse(tempCompany.isActive());
            assertEquals(1L, tempCompany.getId());
            assertEquals("Couchpany", tempCompany.getName());
            return true;
        }));
    }
}
