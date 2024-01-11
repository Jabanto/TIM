package com.jabanto.tims.dao.models;
import javax.persistence.*;
import java.util.Date;

@Entity(name = "item_assignment")
public class Assignment {

    @Id
    @SequenceGenerator(name = "assignment_sequence", sequenceName = "assignment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assignment_sequence")
    private int Id;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.MERGE)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.MERGE)
    @JoinColumn(name = "giver_id")
    private User giverId;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.MERGE)
    @JoinColumn(name = "receiver_id")
    private User receiverId;

    @Column(name = "checkout_date")
    private Date checkOutDate;

    @Column(name = "checkin_date")
    private Date checkInDate;

    public Assignment() {
    }

    public Assignment(Item itemId, User giverId, User receiverId, Date checkOutDate, Date checkInDate) {
        this.item = itemId;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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
                ", itemId=" + item +
                ", giverId=" + giverId +
                ", receiverId=" + receiverId +
                ", checkOutDate=" + checkOutDate +
                ", checkInDate=" + checkInDate +
                '}';
    }
}
