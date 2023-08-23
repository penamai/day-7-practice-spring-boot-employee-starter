package com.thoughtworks.springbootemployee.model;

public class Employee {

    private final Long id;
    private final String name;
    private final Integer age;
    private final String gender;
    private final Integer salary;
    private final Long companyId;
    private boolean isActive;

    public Employee(Long id, String name, Integer age, String gender, Integer salary, Long companyID) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyID;
        this.isActive = true;
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
        return age >= 18 && age <= 65;
    }

    public boolean isActive() {
        return isActive;
    }
}
