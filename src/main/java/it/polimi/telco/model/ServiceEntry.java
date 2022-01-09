package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "service_entry", schema = "telco")
@NamedQuery(name = "ServiceEntry.getServiceEntryById", query = "SELECT se FROM ServiceEntry se  WHERE se.id = ?1 ")
public class ServiceEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String serviceEntryType;

    @NotNull
    private int quantity;

    @NotNull
    private double fee;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceEntryType() {
        return serviceEntryType;
    }

    public void setServiceEntryType(String serviceEntryType) {
        this.serviceEntryType = serviceEntryType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
