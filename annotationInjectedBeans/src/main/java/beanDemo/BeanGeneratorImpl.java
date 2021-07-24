package beanDemo;

import beanDemo.pojos.Company;
import beanDemo.pojos.Employee;
import beanDemo.pojos.Factory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeanGeneratorImpl implements BeanGenerator {

    @Override
    public Employee createEmployee(String name, String position) {
        return new Employee(name, position);
    }

    @Override
    public Factory createFactory(String name, List<Employee> employees) {
        return new Factory(name, employees);
    }

    @Override
    public Company createCompany(List<Employee> employees, List<Factory> factories) {
        return new Company(employees, factories);
    }
}
