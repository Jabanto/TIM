package com.jabanto.tims.dao.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "item")
public class Item {

    //we change Identity to Sequence because SQL Dialect don't support identity key generation
    //@SequenceGenerator(name = "item_sequence", sequenceName = "item_sequence", allocationSize = 1, initialValue = 15)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_sequence")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "item_name")
    private String itemName;
    @Column(name = "remark")
    private String remark;

    @JoinColumn(name = "category_id")
    @OneToOne( fetch = FetchType.EAGER , cascade = CascadeType.MERGE)
    private Category category;

    @Column(name = "item_group")
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @JoinColumn(name = "status_id")
    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Status status;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    List<Assignment> assignmentsAsGiver = new ArrayList<>();

    public Item() {
    }

    public Item(String itemName, String remark, Category category, Status status) {
        this.itemName = itemName;
        this.remark = remark;
        this.category = category;
        this.status = status;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }
    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Item{" +
                "Id=" + Id +
                ", itemName='" + itemName + '\'' +
                ", remark='" + remark + '\'' +
                ", category=" + category +
                ", itemType=" + itemType +
                ", status=" + status +
                '}';
    }
}
