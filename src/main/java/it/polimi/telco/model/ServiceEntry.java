package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "entry", schema = "telco")
public class ServiceEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String type;

    @NotNull
    private int quantity;

    @NotNull
    private double fee;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_id_service")
    private TelcoService service;

    public ServiceEntry(String type, int quantity, double fee) {
        this.type = type;
        this.quantity = quantity;
        this.fee = fee;
    }

    public ServiceEntry() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public TelcoService getService() {
        return service;
    }

    public void setService(TelcoService service) {
        this.service = service;
    }
}
