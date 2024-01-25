package com.jabanto.tims.dao.models;

import javax.persistence.*;

@Entity(name = "user_role")
public class UserRole {

    @Id
    @SequenceGenerator(name = "role_generator", sequenceName = "role_sequence", allocationSize = 1, initialValue = 8)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "role_generator")
    private int Id;

    @Column(name = "role_name")
    private String roleName;

    public UserRole() {

    }

    public UserRole(String roleName) {
        this.roleName = roleName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "Id=" + Id +
                ", role_name='" + roleName + '\'' +
                '}';
    }
}
