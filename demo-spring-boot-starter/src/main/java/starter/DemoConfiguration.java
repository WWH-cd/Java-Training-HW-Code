package starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import starter.pojo.Klass;
import starter.pojo.School;
import starter.pojo.Student;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@ComponentScan("starter")
public class DemoConfiguration {

    @Bean("student100")
    @ConditionalOnMissingBean
    public Student getStudent100() {
        Student student = new Student();
        student.setId(100);
        student.setName("KK100");
        return student;
    }

    @Bean("student123")
    @ConditionalOnMissingBean
    public Student getStudent123() {
        Student student = new Student();
        student.setId(123);
        student.setName("KK123");
        return student;
    }

    @Bean("class1")
    @ConditionalOnMissingBean
    public Klass getKlass() {
        List<Student> students = new ArrayList<>();
        students.add(getStudent100());
        students.add(getStudent123());
        Klass klass = new Klass();
        klass.setStudents(students);
        return klass;
    }

    @Bean("school")
    @ConditionalOnMissingBean
    public School getSchool() {
        return new School();
    }
}
