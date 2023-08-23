package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.exception.NotValidEmployeeAge;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        if(!employee.hasValidAge())
            throw new NotValidEmployeeAge();
        return employeeRepository.addAnEmployee(employee);
    }
}
