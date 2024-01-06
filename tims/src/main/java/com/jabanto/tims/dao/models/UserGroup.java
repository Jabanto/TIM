package com.jabanto.tims.dao.models;

import javax.persistence.*;

@Entity(name = "user_group")
public class UserGroup {

    @Id
    @SequenceGenerator(name = "group_sequence", sequenceName = "group_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_sequence")
    private int Id;

    @Column(name = "group_name")
    private String groupName;


    public UserGroup() {
    }

    public UserGroup(String groupName) {
        this.groupName = groupName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    @Override
    public String toString() {
        return "UserGroup{" +
                "Id=" + Id +
                ", group_name='" + groupName + '\'' +
                '}';
    }
}
