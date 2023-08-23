package com.thoughtworks.springbootemployee.servicetests;

import com.thoughtworks.springbootemployee.exception.InactiveCompanyException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void should_return_updated_company_when_update_given_active_company() {
        Company company = new Company(1L, "Brandname");
        Company updatedCompanyInfo = new Company (null, "BrandNameNew");

        when(mockedCompanyRepository.getCompanyById(company.getId())).thenReturn(company);

        Company updatedCompany = companyService.update(company.getId(), updatedCompanyInfo);

        assertTrue(updatedCompany.isActive());
        assertEquals(company.getId(), updatedCompany.getId());
        assertEquals(updatedCompanyInfo.getName(), updatedCompany.getName());
    }

    @Test
    void should_return_exceptionError_when_update_given_inactive_company() {
        Company company = new Company(1L, "inactivee");
        Company updatedCompanyInfo = new Company(null, "Brandname");
        company.setToInactive();
        when(mockedCompanyRepository.getCompanyById(company.getId())).thenReturn(company);

        InactiveCompanyException inactiveEmployeeException = assertThrows(InactiveCompanyException.class,
                () -> companyService.update(company.getId(), updatedCompanyInfo));

        assertEquals("Company is inactive", inactiveEmployeeException.getMessage());
    }

    @Test
    void should_return_all_companies_when_findAll() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1L, "JAJAJA"));
        companies.add(new Company(2L, "stuq"));
        companies.add(new Company(3L, "woooo"));
        when(mockedCompanyRepository.listAllCompanies()).thenReturn(companies);

        List<Company> retrievedCompanies = companyService.findAll();

        assertThat(companies).hasSameElementsAs(retrievedCompanies);
    }

    @Test
    void should_return_correct_company_when_findById_given_company_Id() {
        Company company = new Company(1L, "Harmony");
        when(mockedCompanyRepository.getCompanyById(company.getId())).thenReturn(company);

        Company retrievedCompany = companyService.findById(company.getId());

        assertEquals(company.getId(), retrievedCompany.getId());
        assertEquals(company.getName(), retrievedCompany.getName());
    }

    @Test
    void should_return_correct_list_of_companies_when_findByPage_given_pageSize_and_pageNumber() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1L, "JAJAJA"));
        companies.add(new Company(2L, "stuq"));
        companies.add(new Company(3L, "woooo"));
        when(mockedCompanyRepository.listCompaniesByPage(1,3)).thenReturn(companies);

        List<Company> retrievedCompanies = companyService.findByPage(1,3);

        assertThat(companies).hasSameElementsAs(retrievedCompanies);
    }
}
