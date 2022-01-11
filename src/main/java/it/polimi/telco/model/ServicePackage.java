package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "service_package", schema = "telco")
public class ServicePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @OneToMany
    private List<TelcoService> services;

    @NotNull
    @OneToMany
    private List<ValidityPeriod> availableValidityPeriods;

    @NotNull
    @OneToMany
    private List<Product> availableOptionalProducts;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TelcoService> getServices() {
        return services;
    }

    public void setServices(List<TelcoService> services) {
        this.services = services;
    }

    public List<ValidityPeriod> getAvailableValidityPeriods() {
        return availableValidityPeriods;
    }

    public void setAvailableValidityPeriods(List<ValidityPeriod> availableValidityPeriods) {
        this.availableValidityPeriods = availableValidityPeriods;
    }

    public List<Product> getAvailableOptionalProducts() {
        return availableOptionalProducts;
    }

    public void setAvailableOptionalProducts(List<Product> availableOptionalProducts) {
        this.availableOptionalProducts = availableOptionalProducts;
    }
}
