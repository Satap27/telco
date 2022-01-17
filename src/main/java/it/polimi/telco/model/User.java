package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;


@Entity
@Table(name = "user", schema = "telco")
@NamedQuery(name = "User.checkCredentials", query = "SELECT u FROM User u  WHERE u.username = ?1 and u.password = ?2")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String password;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String role;

    @NotNull
    private boolean insolvent;

    @NotNull
    @Column(name = "failed_payments")
    private int failedPayments;

    public User() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isInsolvent() {
        return insolvent;
    }

    public void setInsolvent(boolean insolvent) {
        this.insolvent = insolvent;
    }

    public int getFailedPayments() {
        return failedPayments;
    }

    public void setFailedPayments(int failedPayments) {
        this.failedPayments = failedPayments;
    }
}

