package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "package", schema = "telco")
@NamedQueries({
        @NamedQuery(name = "ServicePackage.getAllServicePackages", query = "SELECT DISTINCT a FROM ServicePackage a" +
                " LEFT JOIN FETCH a.services LEFT JOIN FETCH a.availableValidityPeriods"),
        @NamedQuery(name = "ServicePackage.getAllServicePackagesDetailed", query = "SELECT DISTINCT a FROM ServicePackage a" +
                " LEFT JOIN FETCH a.services LEFT JOIN FETCH a.availableValidityPeriods LEFT JOIN FETCH a.availableOptionalProducts")})
public class ServicePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @OneToMany(mappedBy = "servicePackage", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TelcoService> services;

    @NotNull
    @OneToMany(mappedBy = "servicePackage", cascade = CascadeType.ALL)
    private List<ValidityPeriod> availableValidityPeriods;

    @ManyToMany
    @JoinTable(name = "package_product", schema = "telco", joinColumns = @JoinColumn(name = "fk_id_package"), inverseJoinColumns = @JoinColumn(name = "fk_id_product"))
    private List<Product> availableOptionalProducts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        for (TelcoService service: services) {
            service.setServicePackage(this);
        }
    }

    public List<ValidityPeriod> getAvailableValidityPeriods() {
        return availableValidityPeriods;
    }

    public void setAvailableValidityPeriods(List<ValidityPeriod> availableValidityPeriods) {
        this.availableValidityPeriods = availableValidityPeriods;
        for (ValidityPeriod validityPeriod: availableValidityPeriods) {
            validityPeriod.setServicePackage(this);
        }
    }

    public List<Product> getAvailableOptionalProducts() {
        return availableOptionalProducts;
    }

    public void setAvailableOptionalProducts(List<Product> availableOptionalProducts) {
        this.availableOptionalProducts = availableOptionalProducts;
    }
}
