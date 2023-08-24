package com.thoughtworks.springbootemployee.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Employee {

    public static final int MINIMUM_VALID_AGE = 18;
    public static final int MAXIMUM_VALID_AGE = 65;
    private Long id;
    private final String name;
    private Integer age;
    private final String gender;
    private Integer salary;
    private final Long companyId;
    private Boolean isActive;

    @JsonCreator
    public Employee(String name, Integer age, String gender, Integer salary, Long companyId) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
        this.isActive = TRUE;
    }

    public Employee(Long id, String name, Integer age, String gender, Integer salary, Long companyId) {
        this(name, age, gender, salary, companyId);
        this.id = id;
    }

    public Employee(Long id, Employee employee){
        this(employee.getName(), employee.getAge(), employee.getGender(), employee.getSalary(), employee.getCompanyId());
        this.id = id;
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

    public boolean hasValidAge() {
        return age >= MINIMUM_VALID_AGE && age <= MAXIMUM_VALID_AGE;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setToInactive() {
        this.isActive = FALSE;
    }

    public void update(Employee updatedEmployeeInfo) {
        this.age = updatedEmployeeInfo.getAge();
        this.salary = updatedEmployeeInfo.getSalary();
    }
}
