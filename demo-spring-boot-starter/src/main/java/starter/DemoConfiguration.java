package starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import starter.pojo.Klass;
import starter.pojo.School;
import starter.pojo.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Create a customized spring boot starter, in which some beans are created.
 *
 * Purpose of the starter: when multiple modules need those beans, they don't
 * have to create the same beans multiple times. Those modules can inject
 * the dependency of the starter, and call those beans.
 */
@Configuration
public class DemoConfiguration {

    @Bean("student100")
    public Student getStudent100() {
        Student student = new Student();
        student.setId(100);
        student.setName("KK100");
        return student;
    }

    @Bean("student123")
    public Student getStudent123() {
        Student student = new Student();
        student.setId(123);
        student.setName("KK123");
        return student;
    }

    @Bean("class1")
    public Klass getKlass() {
        List<Student> students = new ArrayList<>();
        students.add(getStudent100());
        students.add(getStudent123());
        Klass klass = new Klass();
        klass.setStudents(students);
        return klass;
    }

    @Bean("school")
    public School getSchool() {
        return new School();
    }
}
