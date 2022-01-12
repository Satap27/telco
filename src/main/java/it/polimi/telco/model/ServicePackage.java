package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "package", schema = "telco")
@NamedQuery(name = "ServicePackage.getAllServicePackages", query = "SELECT DISTINCT a FROM ServicePackage a\n" +
        "  LEFT JOIN FETCH a.services")
public class ServicePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @ManyToMany
    @JoinTable(name = "package_service", schema = "telco", joinColumns = @JoinColumn(name = "fk_id_package"), inverseJoinColumns = @JoinColumn(name = "fk_id_service"))
    private List<TelcoService> services;

    @NotNull
    @ManyToMany
    @JoinTable(name = "package_validity_period", schema = "telco", joinColumns = @JoinColumn(name = "fk_id_package"), inverseJoinColumns = @JoinColumn(name = "fk_id_period"))
    private List<ValidityPeriod> availableValidityPeriods;

    @ManyToMany
    @JoinTable(name = "package_product", schema = "telco", joinColumns = @JoinColumn(name = "fk_id_package"), inverseJoinColumns = @JoinColumn(name = "fk_id_product"))
    private List<Product> availableOptionalProducts;

    public void setId(Long id) {
        this.id = id;
    }

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
