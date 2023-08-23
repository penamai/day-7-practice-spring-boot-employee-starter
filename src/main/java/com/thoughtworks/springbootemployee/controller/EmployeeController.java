package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> listAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findByGender(@RequestParam String gender) {
        return employeeService.findByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addAnEmployee(@RequestBody Employee employee){
        return employeeService.create(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployeeById(@PathVariable Long id, @RequestBody Employee updatedEmployeeInfo){
        return employeeService.update(id, updatedEmployeeInfo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeById(@PathVariable Long id){
        employeeService.delete(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> listEmployeesByPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return employeeService.findByPage(pageNumber, pageSize);
    }
}
