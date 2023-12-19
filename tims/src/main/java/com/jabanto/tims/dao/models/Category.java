package com.jabanto.tims.dao.models;

import javax.persistence.*;

@Entity(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "category_name")
    private String Name;

    public Category() {
    }

    public Category(String name) {
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
        return "Category{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                '}';
    }
}
