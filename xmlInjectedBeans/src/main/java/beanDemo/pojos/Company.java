package beanDemo.pojos;

import lombok.Data;

import java.util.List;

@Data
public class Company {
    List<Employee> employees;
    List<Factory> factories;
}
