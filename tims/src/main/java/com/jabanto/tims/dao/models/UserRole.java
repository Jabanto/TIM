package com.jabanto.tims.dao.models;

import javax.persistence.*;

@Entity(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private String role_name;

    public UserRole() {
    }

    public UserRole(String role_name) {
        this.role_name = role_name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "Id=" + Id +
                ", role_name='" + role_name + '\'' +
                '}';
    }
}
