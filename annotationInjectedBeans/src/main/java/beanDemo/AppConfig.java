package beanDemo;

import beanDemo.pojos.Employee;
import beanDemo.pojos.Factory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean(value = "emp01")
    public Employee getEmp01() {
        return new Employee("Michael FeraDay", "Engineer");
    }

    @Bean(value = "emp02")
    public Employee getEmp02() {
        return new Employee("Elon Musk", "manager");
    }

    @Bean("factory")
    public Factory getFactory01() {
        List<Employee> employees = new ArrayList<>();
        employees.add(getEmp01());
        employees.add(getEmp02());
        return new Factory("Mountain View", employees);
    }
}
