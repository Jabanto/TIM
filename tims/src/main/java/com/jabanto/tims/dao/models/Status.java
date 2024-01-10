package com.jabanto.tims.dao.models;
import javax.persistence.*;

@Entity(name = "status")
public class Status {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "status_name")
    private String name;

    public Status() {
    }

    public Status(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Status{" +
                "Id=" + Id +
                ", Name='" + name + '\'' +
                '}';
    }
}
