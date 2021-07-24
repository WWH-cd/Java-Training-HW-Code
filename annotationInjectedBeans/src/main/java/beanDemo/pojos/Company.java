package beanDemo.pojos;

import lombok.Data;

import java.util.List;

@Data
public class Company {
    List<Employee> employees;
    List<Factory> factories;


    public Company(List<Employee> employees, List<Factory> factories) {
        this.employees = employees;
        this.factories = factories;
    }
}
