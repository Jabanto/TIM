package com.jabanto.tims.dao.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "user_token" ,nullable = false)
    private String token;

    @Column(name = "create_at")
    private LocalDateTime createdAt;
    @Column(name = "expire_at")
    private LocalDateTime expiresAt;

    @Column(name = "confirm_at")
    private LocalDateTime confirmAt;

    @ManyToOne
    @JoinColumn( name = "user_id", nullable = false)
    private User user;

    public ConfirmationToken(User user,String token, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.user = user;
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getConfirmAt() {
        return confirmAt;
    }

    public void setConfirmAt(LocalDateTime confirmAt) {
        this.confirmAt = confirmAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
