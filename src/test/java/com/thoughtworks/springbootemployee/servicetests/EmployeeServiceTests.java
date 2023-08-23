package com.thoughtworks.springbootemployee.servicetests;

import com.thoughtworks.springbootemployee.exception.InactiveEmployeeException;
import com.thoughtworks.springbootemployee.exception.NotValidEmployeeAgeException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EmployeeServiceTests {
    @Autowired
    private EmployeeService employeeService;

    private EmployeeRepository mockedEmployeeRepository;

    @BeforeEach
    void setup() {
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }

    @Test
    void should_return_created_employee_when_create_given_employee_with_valid_age() {
        Employee employee = new Employee(null, "Lucy", 20, "Female", 3000, 1L);
        Employee savedEmployee = new Employee(1L, "Lucy", 20, "Female", 3000, 1L);
        when(mockedEmployeeRepository.addAnEmployee(employee)).thenReturn(savedEmployee);

        Employee createdEmployee = employeeService.create(employee);

        assertEquals(1L, createdEmployee.getId());
        assertEquals("Lucy", createdEmployee.getName());
        assertEquals(20, createdEmployee.getAge());
        assertEquals("Female", createdEmployee.getGender());
        assertEquals(3000, createdEmployee.getSalary());
        assertEquals(1L, createdEmployee.getCompanyId());
        assertTrue(createdEmployee.isActive());
    }

    @Test
    void should_return_exception_error_when_create_given_employee_with_age_less_than_18() {
        Employee employee = new Employee(null, "Lucy", 16, "Female", 5000, 1L);

        NotValidEmployeeAgeException notValidEmployeeAgeException = assertThrows(NotValidEmployeeAgeException.class,
                () -> employeeService.create(employee));

        assertEquals("Employee must be 18~65 years old", notValidEmployeeAgeException.getMessage());
    }

    @Test
    void should_return_exception_error_when_create_given_employee_with_age_greater_than_65() {
        Employee employee = new Employee(null, "Lucy", 80, "Female", 10000, 1L);

        NotValidEmployeeAgeException notValidEmployeeAgeException = assertThrows(NotValidEmployeeAgeException.class,
                () -> employeeService.create(employee));

        assertEquals("Employee must be 18~65 years old", notValidEmployeeAgeException.getMessage());
    }

    @Test
    void should_set_active_false_when_delete_given_employee_service_and_employee_id() {
        Employee employee = new Employee(1L, "Lucy", 35, "Female", 8000, 1L);

        when(mockedEmployeeRepository.findById(employee.getId())).thenReturn(employee);

        employeeService.delete(employee.getId());

        mockedEmployeeRepository.updateEmployeeById(eq(employee.getId()), argThat( tempEmployee -> {
            assertFalse(tempEmployee.isActive());
            assertEquals("Lucy", tempEmployee.getName());
            assertEquals(35, tempEmployee.getAge());
            assertEquals("Female", tempEmployee.getGender());
            assertEquals(8000, tempEmployee.getSalary());
            assertEquals(1L, tempEmployee.getCompanyId());
            return true;
        }));
    }

    @Test
    void should_return_updated_employee_when_update_employee_given_active_employee() {
        Employee employee = new Employee(1L, "Delilah", 50, "Female", 10000, 2L);
        Employee updatedEmployeeInfo = new Employee(null, 51, "Female", 11000, null);

        when(mockedEmployeeRepository.findById(employee.getId())).thenReturn(employee);

        employeeService.update(employee.getId(), updatedEmployeeInfo);

        mockedEmployeeRepository.updateEmployeeById(eq(employee.getId()), argThat( tempEmployee -> {
            assertTrue(tempEmployee.isActive());
            assertEquals(employee.getName(), tempEmployee.getName());
            assertEquals(updatedEmployeeInfo.getAge(), tempEmployee.getAge());
            assertEquals(employee.getGender(), tempEmployee.getGender());
            assertEquals(updatedEmployeeInfo.getSalary(), tempEmployee.getSalary());
            assertEquals(employee.getCompanyId(), tempEmployee.getCompanyId());
            return true;
        }));
    }

    @Test
    void should_return_exceptionError_when_update_employee_given_inactive_employee() {
        Employee employee = new Employee(1L, "Delilah", 50, "Female", 10000, 2L);
        Employee updatedEmployeeInfo = new Employee(null, 51, "Female", 11000, null);
        employee.setToInactive();
        when(mockedEmployeeRepository.findById(employee.getId())).thenReturn(employee);

        InactiveEmployeeException inactiveEmployeeException = assertThrows(InactiveEmployeeException.class,
                () -> employeeService.update(employee.getId(), updatedEmployeeInfo));

        assertEquals("Employee is inactive", inactiveEmployeeException.getMessage());
    }

    @Test
    void should_return_all_employees_when_findAll_employees_given_employeeRepository() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "Ababa", 20, "Female", 10000, 1L));
        employees.add(new Employee(2L, "Brrr", 54, "Male", 2000, 2L));
        employees.add(new Employee(3L, "Cheess", 35, "Male", 18000, 1L));
        when(mockedEmployeeRepository.getAllEmployees()).thenReturn(employees);

        List<Employee> retrievedEmployees = employeeService.findAll();

        assertThat(employees).hasSameElementsAs(retrievedEmployees);
    }

    @Test
    void should_return_correct_employee_when_findById_given_employee_id() {
        Employee employee = new Employee(1L, "Ababa", 20, "Female", 10000, 1L);
        when(mockedEmployeeRepository.findById(employee.getId())).thenReturn(employee);

        Employee retrievedEmployee = employeeService.findById(employee.getId());

        assertEquals(employee.getId(), retrievedEmployee.getId());
        assertEquals(employee.getName(), retrievedEmployee.getName());
        assertEquals(employee.getAge(), retrievedEmployee.getAge());
        assertEquals(employee.getGender(), retrievedEmployee.getGender());
        assertEquals(employee.getSalary(), retrievedEmployee.getSalary());
        assertEquals(employee.getCompanyId(), retrievedEmployee.getCompanyId());
    }
    
    @Test
    void should_return_female_employees_when_findByGender_given_female_parameter() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "Ababa", 20, "Female", 10000, 1L));
        employees.add(new Employee(2L, "Blaire", 54, "Female", 2000, 2L));
        employees.add(new Employee(3L, "Claire", 35, "Female", 18000, 1L));
        when(mockedEmployeeRepository.findByGender("Female")).thenReturn(employees);

        List<Employee> retrievedEmployees = employeeService.findByGender("Female");

        assertThat(employees).hasSameElementsAs(retrievedEmployees);
    }
}
