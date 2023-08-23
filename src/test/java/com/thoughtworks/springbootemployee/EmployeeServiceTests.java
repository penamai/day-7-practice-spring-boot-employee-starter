package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.exception.NotValidEmployeeAge;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EmployeeServiceTests {
    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;
    @BeforeEach
    void setup(){
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
    }
    @Test
    void should_return_created_employee_when_create_given_employee_with_valid_age() {
        Employee employee = new Employee(null, "Lucy", 20, "Female", 3000, 1L);
        Employee savedEmployee = new Employee(1L, "Lucy", 20, "Female", 3000, 1L);
        when(employeeRepository.addAnEmployee(employee)).thenReturn(savedEmployee);

        Employee createdEmployee = employeeService.create(employee);

        Assertions.assertEquals(savedEmployee.getId(), createdEmployee.getId());
        Assertions.assertEquals(savedEmployee.getName(), createdEmployee.getName());
        Assertions.assertEquals(savedEmployee.getAge(), createdEmployee.getAge());
        Assertions.assertEquals(savedEmployee.getGender(), createdEmployee.getGender());
        Assertions.assertEquals(savedEmployee.getSalary(), createdEmployee.getSalary());
        Assertions.assertEquals(savedEmployee.getCompanyId(), createdEmployee.getCompanyId());
    }

    @Test
    void should_return_exception_error_when_create_given_employee_with_age_less_than_18() {
        Employee employee = new Employee(null, "Lucy", 16, "Female", 5000, 1L);

        NotValidEmployeeAge notValidEmployeeAge = assertThrows(NotValidEmployeeAge.class,
                () -> employeeService.create(employee));

        Assertions.assertEquals("Employee must be 18~65 years old", notValidEmployeeAge.getMessage());
    }

    @Test
    void should_return_exception_error_when_create_given_employee_with_age_greater_than_65() {
        Employee employee = new Employee(null, "Lucy", 80, "Female", 10000, 1L);

        NotValidEmployeeAge notValidEmployeeAge = assertThrows(NotValidEmployeeAge.class,
                () -> employeeService.create(employee));

        Assertions.assertEquals("Employee must be 18~65 years old", notValidEmployeeAge.getMessage());
    }
}
