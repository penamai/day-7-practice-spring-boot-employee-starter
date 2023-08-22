package com.thoughtworks.springbootemployee.controller;

public class Employee {

    private final Long id;
    private final String name;
    private final Integer age;
    private final String gender;
    private final Integer salary;
    private final Long companyId;

    public Employee(Long id, String name, Integer age, String gender, Integer salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = null;
    }

    public Employee(Employee employee, Long companyId) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.age = employee.getAge();
        this.gender = employee.getGender();
        this.salary = employee.getSalary();
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public Long getCompanyId(){
        return companyId;
    }
}
