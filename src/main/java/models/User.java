
package models;

import java.util.Date;

public class User {
    public User(String firstName, String lastName, String email, String password, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }

    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public Date birthday;
}