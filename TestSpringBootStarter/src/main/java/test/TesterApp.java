package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import starter.pojo.Student;

@SpringBootApplication
public class TesterApp implements CommandLineRunner {
    @Autowired @Qualifier("student100")
    Student student100;

    public static void main(String[] args) {
        SpringApplication.run(TesterApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        student100.init();
    }
}
