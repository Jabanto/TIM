package com.jabanto.tims.dao.models;


import javax.persistence.*;
import java.util.Date;

@Entity(name = "item_assignment")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Item itemId;

    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "giver_id")
    private User giverId;

    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_id")
    private User receiverId;

    @Column(name = "checkout_date")
    private Date checkOutDate;

    @Column(name = "checkin_date")
    private Date checkInDate;

    public Assignment() {
    }

    public Assignment(Item itemId, User giverId, User receiverId, Date checkOutDate, Date checkInDate) {
        this.itemId = itemId;
        this.giverId = giverId;
        this.receiverId = receiverId;
        this.checkOutDate = checkOutDate;
        this.checkInDate = checkInDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public User getGiverId() {
        return giverId;
    }

    public void setGiverId(User giverId) {
        this.giverId = giverId;
    }

    public User getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(User receiverId) {
        this.receiverId = receiverId;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "Id=" + Id +
                ", itemId=" + itemId +
                ", giverId=" + giverId +
                ", receiverId=" + receiverId +
                ", checkOutDate=" + checkOutDate +
                ", checkInDate=" + checkInDate +
                '}';
    }
}
