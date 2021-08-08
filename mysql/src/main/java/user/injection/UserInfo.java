package user.injection;

import lombok.Data;

@Data
public class UserInfo {
    private int id;
    private String username;
    private String password;
    private int gender;
    private String ssn;
    private int age;
    private int status;
    private long createdAt;
    private long updatedAt;

    @Override
    public String toString() {
        return String.format("UserInfo: [ID: %d, Username: %s, Password: %s, Geneder: %d, " +
                "SSN: %s, Age: %d, Status: %d, Created At: %d, Updated: %d");
    }
}
