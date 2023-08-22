package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> listAll() {
        return employeeRepository.listAll();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findByGender(@RequestParam String gender) {
        return employeeRepository.findByGender(gender);
    }

    @PostMapping
    public Employee addAnEmployee(@RequestBody Employee employee){
        return employeeRepository.addAnEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployeeById(@PathVariable Long id, @RequestBody Employee updatedEmployeeInfo){
        return employeeRepository.updateEmployeeById(id, updatedEmployeeInfo);
    }

    @DeleteMapping("/{id}")
    public List<Employee> deleteEmployeeById(@PathVariable Long id){
        return employeeRepository.deleteEmployeeById(id);
    }
}
