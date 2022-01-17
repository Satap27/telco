package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "alert", schema = "telco")
public class Alert implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "fk_id_user")
    private User user;

    @NotNull
    private double amount;

    @NotNull
    @Column(name = "rejection_time")
    private Date rejectionTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getRejectionTime() {
        return rejectionTime;
    }

    public void setRejectionTime(Date rejectionTime) {
        this.rejectionTime = rejectionTime;
    }
}

