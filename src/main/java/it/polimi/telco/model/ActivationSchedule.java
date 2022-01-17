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
    @JoinTable(name = "schedule_service", schema = "telco", joinColumns = @JoinColumn(name = "fk_id_schedule"), inverseJoinColumns = @JoinColumn(name = "fk_id_service"))
    private List<TelcoService> services;

    @NotNull
    @OneToOne
    @JoinColumn(name = "fk_id_user")
    private User user;

    @ManyToMany
    @JoinTable(name = "schedule_product", schema = "telco", joinColumns = @JoinColumn(name = "fk_id_schedule"), inverseJoinColumns = @JoinColumn(name = "fk_id_product"))
    private List<Product> optionalProducts;

    @NotNull
    @Column(name = "date_of_activation")
    private Date dateOfActivation;

    @NotNull
    @Column(name = "date_of_deactivation")
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
