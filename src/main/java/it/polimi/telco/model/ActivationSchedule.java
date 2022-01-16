package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "activation_schedule", schema = "telco")
public class ActivationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @OneToMany
    private List<TelcoService> services;

    @NotNull
    @OneToMany
    private List<Product> optionalProducts;

    @NotNull
    private Date dateOfActivation;

    @NotNull
    private Date dateOfDeactivation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<TelcoService> getServices() {
        return services;
    }

    public void setServices(List<TelcoService> services) {
        this.services = services;
    }

    public List<Product> getOptionalProducts() {
        return optionalProducts;
    }

    public void setOptionalProducts(List<Product> optionalProducts) {
        this.optionalProducts = optionalProducts;
    }

    public Date getDateOfActivation() {
        return dateOfActivation;
    }

    public void setDateOfActivation(Date dateOfActivation) {
        this.dateOfActivation = dateOfActivation;
    }

    public Date getDateOfDeactivation() {
        return dateOfDeactivation;
    }

    public void setDateOfDeactivation(Date dateOfDeactivation) {
        this.dateOfDeactivation = dateOfDeactivation;
    }
}
