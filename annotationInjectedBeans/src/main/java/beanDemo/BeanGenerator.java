package beanDemo;

import beanDemo.pojos.Company;
import beanDemo.pojos.Employee;
import beanDemo.pojos.Factory;

import java.util.List;

public interface BeanGenerator {
    Employee createEmployee(String name, String position);

    Factory createFactory(String name, List<Employee> employees);

    Company createCompany(List<Employee> employees, List<Factory> factories);
}
