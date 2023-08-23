package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.Employee;
import com.thoughtworks.springbootemployee.controller.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeApiTests {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mockMvcClient;
    @BeforeEach
    void cleanupEmployeeData() {
        employeeRepository.cleanup();
    }

    @Test
    void should_return_employees_when_get_employees_given_employeeRepository() throws Exception {
        Employee alice = employeeRepository.addAnEmployee(new Employee(1L, "Alice", 25, "Female", 10000, 1L));

        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(alice.getId()))
                .andExpect(jsonPath("$[0].name").value(alice.getName()))
                .andExpect(jsonPath("$[0].age").value(alice.getAge()))
                .andExpect(jsonPath("$[0].gender").value(alice.getGender()))
                .andExpect(jsonPath("$[0].salary").value(alice.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(alice.getCompanyId()));
    }

    @Test
    void should_return_right_employee_when_get_employee_given_employee_id() throws Exception {
        Employee alice = employeeRepository.addAnEmployee(new Employee(1L, "Alice", 25, "Female", 10000, 1L));
        employeeRepository.addAnEmployee(new Employee(2L, "Bob", 30, "Male", 15000, 2L));

        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + alice.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(alice.getId()))
                .andExpect(jsonPath("$.name").value(alice.getName()))
                .andExpect(jsonPath("$.age").value(alice.getAge()))
                .andExpect(jsonPath("$.gender").value(alice.getGender()))
                .andExpect(jsonPath("$.salary").value(alice.getSalary()))
                .andExpect(jsonPath("$.companyId").value(alice.getCompanyId()));
    }

        @Test
    void should_return_404_not_found_when_get_employee_given_not_existing_employee_id() throws Exception {
        Long notExistingEmployeeId = 99L;
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + notExistingEmployeeId))
                .andExpect(status().isNotFound());
    }
    @Test
    void should_return_list_of_employees_when_get_employees_given_a_gender() throws Exception {
        Employee alice = employeeRepository.addAnEmployee(new Employee(1L, "Alice", 25, "Female", 10000, 1L));
        Employee bob = employeeRepository.addAnEmployee(new Employee(2L, "Bob", 30, "Male", 15000, 2L));

        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees").param("gender","Female"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(alice.getId()))
                .andExpect(jsonPath("$[0].name").value(alice.getName()))
                .andExpect(jsonPath("$[0].age").value(alice.getAge()))
                .andExpect(jsonPath("$[0].gender").value(alice.getGender()))
                .andExpect(jsonPath("$[0].salary").value(alice.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(alice.getCompanyId()));
    }
}
