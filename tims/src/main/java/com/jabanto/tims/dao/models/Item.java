package com.jabanto.tims.dao.models;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "remark")
    private String remark;

    @JoinColumn(name = "category_id")
    @OneToOne( fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private Category category;

    @JoinColumn(name = "group_id")
    @OneToOne( fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private UserGroup userGroup;

    @JoinColumn(name = "status_id")
    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Status status;

    public Item() {
    }

    public Item(String itemName, String remark, Category category, UserGroup userGroup, Status status) {
        this.itemName = itemName;
        this.remark = remark;
        this.category = category;
        this.userGroup = userGroup;
        this.status = status;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
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

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
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
                "id=" + Id +
                ", item_name='" + itemName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
