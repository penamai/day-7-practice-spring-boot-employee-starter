package com.thoughtworks.springbootemployee.servicetests;

import com.thoughtworks.springbootemployee.exception.InactiveCompanyException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
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
    private EmployeeRepository mockedEmployeeRepository;

    @BeforeEach
    void setup() {
        mockedCompanyRepository = mock(CompanyRepository.class);
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        companyService = new CompanyService(mockedCompanyRepository, mockedEmployeeRepository);
    }

    @Test
    void should_return_created_company_when_create_company_given_company() {
        Company company = new Company("Company name");
        Company savedCompany = new Company(1L, "Company name");
        when(mockedCompanyRepository.add(company)).thenReturn(savedCompany);

        Company createdCompany = companyService.create(company);

        assertEquals(1L, createdCompany.getId());
        assertEquals("Company name", createdCompany.getName());
    }

    @Test
    void should_set_active_false_when_delete_given_company_id() {
        Company company = new Company(1L, "Couchpany");

        when(mockedCompanyRepository.findById(company.getId())).thenReturn(company);

        companyService.delete(company.getId());

        mockedCompanyRepository.update(eq(company.getId()), argThat(tempCompany -> {
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
        Company updatedCompany = new Company(company.getId(), updatedCompanyInfo.getName());

        when(mockedCompanyRepository.findById(company.getId())).thenReturn(company);
        when(mockedCompanyRepository.update(company.getId(),updatedCompanyInfo)).thenReturn(updatedCompany);

        Company retrievedCompany = companyService.update(company.getId(), updatedCompanyInfo);

        assertTrue(retrievedCompany.isActive());
        assertEquals(company.getId(), retrievedCompany.getId());
        assertEquals(updatedCompanyInfo.getName(), retrievedCompany.getName());
    }

    @Test
    void should_return_exceptionError_when_update_given_inactive_company() {
        Company company = new Company(1L, "inactivee");
        Company updatedCompanyInfo = new Company(null, "Brandname");
        company.setToInactive();
        when(mockedCompanyRepository.findById(company.getId())).thenReturn(company);

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
        when(mockedCompanyRepository.findAll()).thenReturn(companies);

        List<Company> retrievedCompanies = companyService.findAll();

        assertThat(companies).hasSameElementsAs(retrievedCompanies);
    }

    @Test
    void should_return_correct_company_when_findById_given_company_Id() {
        Company company = new Company(1L, "Harmony");
        when(mockedCompanyRepository.findById(company.getId())).thenReturn(company);

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
        when(mockedCompanyRepository.findByPage(1,3)).thenReturn(companies);

        List<Company> retrievedCompanies = companyService.findByPage(1,3);

        assertThat(companies).hasSameElementsAs(retrievedCompanies);
    }

    @Test
    void should_return_employees_of_company_when_findAllEmployees_given_company_id() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "Ababa", 20, "Female", 10000, 1L));
        employees.add(new Employee(1L, "Brrr", 54, "Male", 2000, 2L));
        employees.add(new Employee(1L, "Cheess", 35, "Male", 18000, 1L));
        when(mockedEmployeeRepository.findByCompanyId(1L)).thenReturn(employees);

        List<Employee> retrievedEmployees = companyService.findAllEmployees(1L);

        assertThat(employees).hasSameElementsAs(retrievedEmployees);
    }
}
