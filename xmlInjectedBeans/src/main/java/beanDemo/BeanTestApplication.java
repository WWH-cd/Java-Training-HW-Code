package beanDemo;

import beanDemo.pojos.Company;
import beanDemo.pojos.Employee;
import beanDemo.pojos.Factory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTestApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Employee emp01 = (Employee) context.getBean("emp01");
        System.out.println(emp01.toString());

        Factory factory = (Factory) context.getBean("factory");
        System.out.println(factory.toString());

        Company company = (Company) context.getBean("company");
        System.out.println(company.toString());
    }
}
