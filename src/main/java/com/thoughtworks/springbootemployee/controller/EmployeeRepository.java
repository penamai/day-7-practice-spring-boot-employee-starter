package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.controller.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();

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
        employees.add(employee);
        return employee;
    }

    public Employee updateEmployeeById(Long id, Employee updatedEmployeeInfo) {
        Employee employeeToBeUpdated = findById(id);
        Employee updatedEmployee = new Employee(id, employeeToBeUpdated.getName(), updatedEmployeeInfo.getAge(), employeeToBeUpdated.getGender(), updatedEmployeeInfo.getSalary(), employeeToBeUpdated.getCompanyId());

        employees.set(employees.indexOf(employeeToBeUpdated), updatedEmployee);
        return updatedEmployee;
    }

    public void deleteEmployeeById(Long id) {
        employees.remove(findById(id));
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
}
