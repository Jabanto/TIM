package com.jabanto.tims.dao.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "user_group")
    private UserGroup userGroup;

    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;

    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "user_role")
    private UserRole userRole;

    public User() {
    }

    public User(String firstName, String lastName,UserGroup userGroup, String password, String email, UserRole userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userGroup = userGroup;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", user_firstname='" + firstName + '\'' +
                ", user_lastname='" + lastName + '\'' +
                ", user_group=" + userGroup +
                ", user_password='" + password + '\'' +
                ", user_email='" + email + '\'' +
                ", user_role=" + userRole +
                '}';
    }
}
