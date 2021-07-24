package beanDemo;

import beanDemo.pojos.Employee;
import beanDemo.pojos.Factory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class BeanAnnotationTestApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        /**
         * Method 1:
         * use @Bean to create static beans, and beans are
         * created and contained in AppConfig.
         */
//        context.register(AppConfig.class);
//        context.refresh();
//        Employee emp01 = (Employee) context.getBean("emp01");
//        System.out.println(emp01.toString());
//        Factory factory = (Factory) context.getBean("factory");
//        System.out.println(factory.toString());

        /**
         * Method 2:
         * AppConfig2 as a Configuration does not provide any configurations.
         * But it has ComponentScan, which discovers a service, BeanGenerator.
         * BeanGenerator contains methods to create beans.
         */
        context.register(AppConfig2.class);
        context.refresh();
        BeanGenerator beanGenerator = context.getBean(BeanGenerator.class);
        Employee emp01 = beanGenerator.createEmployee("Tod Wing", "staff");
        System.out.println(emp01.toString());
        List<Employee> employees = new ArrayList<>();
        employees.add(emp01);
        Factory factory = beanGenerator.createFactory("station X", employees);
        System.out.println(factory.toString());

    }
}
