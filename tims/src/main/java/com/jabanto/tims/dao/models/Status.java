package com.jabanto.tims.dao.models;


import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;

@Entity(name = "status")
public class Status {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "status_name")
    private String Name;

    public Status() {
    }

    public Status(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "Status{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                '}';
    }
}
