package model;

import javax.persistence.*;

/**
 * Created by Raul on 23/11/2017.
 */

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int userId;
    private String surname;
    private String firstName;
    private Role role;
    private String email;
    private String password;

    public User(String surname, String firstName, Role role, String email, String password) {
        this.surname = surname;
        this.firstName = firstName;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(){

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
