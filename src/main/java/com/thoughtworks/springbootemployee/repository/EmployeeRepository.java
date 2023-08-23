package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();

    public static final long DEFAULT_INCREMENT = 1L;

    static {
        employees.add(new Employee(1L, "Alice", 30, "Female", 5000, 1L));
        employees.add(new Employee(2L, "Bob", 31, "Male", 5000, 2L));
        employees.add(new Employee(3L, "Carl", 32, "Male", 5000, 3L));
        employees.add(new Employee(4L, "David", 33, "Male", 5000, 4L));
        employees.add(new Employee(5L, "Ellen", 34, "Female", 5000, 5L));
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public Employee findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee addAnEmployee(Employee employee) {
        Employee newEmployee = new Employee(generateNextId(), employee);
        employees.add(newEmployee);
        return newEmployee;
    }

    private Long generateNextId() {
         if(employees.isEmpty()) {
             return DEFAULT_INCREMENT;
         }
         Optional<Employee> employeeWithMaxId = employees.stream()
                                        .max(Comparator.comparingLong(Employee::getId));
         return employeeWithMaxId.get().getId() + DEFAULT_INCREMENT;
    }

    public Employee updateEmployeeById(Long id, Employee updatedEmployeeInfo) {
        Employee employeeToBeUpdated = findById(id);
        employeeToBeUpdated.update(updatedEmployeeInfo);

        return employeeToBeUpdated;
    }

    public List<Employee> listEmployeesByPage(Integer pageNumber, Integer pageSize) {
        int fromIndex = pageSize*(pageNumber - 1);
        int toIndex = fromIndex + pageSize;
        if(toIndex > employees.size())
            toIndex = employees.size();
        return employees.subList(fromIndex, toIndex);
    }

    public List<Employee> getEmployeesByCompanyId(Long companyId) {
        return employees.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }

    public void cleanup() {
        employees.clear();
    }

    public void setToInactive(Long id) {
        Employee employeeToDelete = findById(id);
        employeeToDelete.setToInactive();
    }
}
