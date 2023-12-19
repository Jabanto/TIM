package com.jabanto.tims.dao.models;

import javax.persistence.*;

@Entity(name = "user_group")
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private String group_name;


    public UserGroup() {
    }

    public UserGroup(String group_name) {
        this.group_name = group_name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
    @Override
    public String toString() {
        return "UserGroup{" +
                "Id=" + Id +
                ", group_name='" + group_name + '\'' +
                '}';
    }
}
