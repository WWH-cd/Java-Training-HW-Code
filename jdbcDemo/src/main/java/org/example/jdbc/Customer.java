package org.example.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
    private long id;
    private String firstName, lastName;

    @Override
    public String toString() {
        return String.format("Customer[id=%d, firstName=%s, lastName=%s]",
                id, firstName, lastName);
    }
}
