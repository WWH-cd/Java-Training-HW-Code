package beanDemo.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class Factory {
    String name;
    List<Employee> employees;
}
