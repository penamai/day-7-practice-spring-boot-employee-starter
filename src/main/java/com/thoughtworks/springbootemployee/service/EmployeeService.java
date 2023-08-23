package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.NotValidEmployeeAge;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        if(!employee.hasValidAge())
            throw new NotValidEmployeeAge();
        return employeeRepository.addAnEmployee(employee);
    }

    public void delete(Long id) {
        employeeRepository.setToInactive(id);
    }

    public Employee update(Long id, Employee updatedEmployeeInfo) {
        return employeeRepository.updateEmployeeById(id, updatedEmployeeInfo);
    }
}
