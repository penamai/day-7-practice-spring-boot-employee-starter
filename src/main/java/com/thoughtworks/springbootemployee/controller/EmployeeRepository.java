package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();

    static {
        employees.add(new Employee(1l, "Alice", 30, "Female", 5000));
        employees.add(new Employee(2l, "Bob", 31, "Male", 5000));
        employees.add(new Employee(3l, "Carl", 32, "Male", 5000));
        employees.add(new Employee(4l, "David", 33, "Male", 5000));
        employees.add(new Employee(5l, "Ellen", 34, "Female", 5000));
    }

    public List<Employee> listAll() {
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
}
