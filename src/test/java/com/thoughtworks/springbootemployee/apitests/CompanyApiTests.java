package com.thoughtworks.springbootemployee.apitests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyApiTests {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MockMvc mockMvcClient;

    @BeforeEach
    void cleanupCompanyData() {
        companyRepository.cleanup();
    }

    @Test
    void should_return_companies_when_get_companies_given_companyRepository() throws Exception {
        Company company = companyRepository.addACompany(new Company("Bruhh Inc."));

        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].name").value(company.getName()));
    }

    @Test
    void should_return_correct_company_when_get_company_given_company_id() throws Exception {
        Company firstCompany = companyRepository.addACompany(new Company("Bruhh Inc."));
        companyRepository.addACompany(new Company("secondCompany"));

        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + firstCompany.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(firstCompany.getId()))
                .andExpect(jsonPath("$.name").value(firstCompany.getName()));
    }

    @Test
    void should_return_404_not_found_when_get_company_given_not_existing_company_id() throws Exception {
        long notExistingCompanyId = 100L;
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + notExistingCompanyId))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_list_of_employees_when_get_company_employees_given_company_id() throws Exception {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        employeeRepository.cleanup();
        Employee employee = employeeRepository.addAnEmployee(new Employee("Chris", 23, "Male", 9090, 1L));
        Company company = companyRepository.addACompany(new Company("Bruhhhh Corp."));

        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + company.getId() + "/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(employee.getId()))
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].age").value(employee.getAge()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(employee.getCompanyId()));
    }

    @Test
    void should_return_company_created_when_post_company_given_company_with_JSON_Format() throws Exception {
        Company company = companyRepository.addACompany(new Company("Bruhhhh Corp."));

        mockMvcClient.perform(MockMvcRequestBuilders.post("/companies").contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(company)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(company.getName()));
    }

    @Test
    void should_return_company_updated_when_put_company_given_company_id_with_JSON_format() throws Exception{
        companyRepository.addACompany(new Company("Bruhhhh Corp."));
        Company initialCompanyInfo = companyRepository.listAllCompanies().get(0);
        Company updatedCompanyInfo = new Company(null, "Rebranding Inc");

        mockMvcClient.perform(MockMvcRequestBuilders.put("/companies/" + initialCompanyInfo.getId()).contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(updatedCompanyInfo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(initialCompanyInfo.getId()))
                .andExpect(jsonPath("$.name").value(updatedCompanyInfo.getName()));
    }

    @Test
    void should_delete_and_return_no_content_when_delete_company_given_company_id() throws Exception {
        companyRepository.addACompany(new Company("Bruhhhh Corp."));
        Company companyToDelete = companyRepository.listAllCompanies().get(0);

        mockMvcClient.perform(MockMvcRequestBuilders.delete("/companies/" + companyToDelete.getId()))
                .andExpect(status().isNoContent());
    }
}
