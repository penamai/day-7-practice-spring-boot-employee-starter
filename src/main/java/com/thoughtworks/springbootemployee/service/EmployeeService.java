package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.InactiveEmployeeException;
import com.thoughtworks.springbootemployee.exception.NotValidEmployeeAgeException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        if(!employee.hasValidAge())
            throw new NotValidEmployeeAgeException();
        return employeeRepository.addAnEmployee(employee);
    }

    public void delete(Long id) {
        employeeRepository.setToInactive(id);
    }

    public Employee update(Long id, Employee updatedEmployeeInfo) {
        Employee employeeToUpdate = employeeRepository.findById(id);
        if(!employeeToUpdate.isActive())
            throw new InactiveEmployeeException();

        return employeeRepository.updateEmployeeById(id, updatedEmployeeInfo);
    }

    public List<Employee> findAll() {
        return employeeRepository.getAllEmployees();
    }

    public Employee findById(Long id){
        return employeeRepository.findById(id);
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public List<Employee> findByPage(Integer pageNumber, Integer pageSize) {
        return employeeRepository.listEmployeesByPage(pageNumber, pageSize);
    }
}
