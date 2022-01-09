package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "service_subscription", schema = "telco")
@NamedQuery(name = "ServiceSubscription.getServiceSubscriptionById", query = "SELECT ss FROM ServiceSubscription ss  WHERE ss.id = ?1 ")
public class ServiceSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @OneToOne
    private ServicePackage servicePackage;

    @NotNull
    @OneToOne
    private ValidityPeriod validityPeriod;

    @NotNull
    @OneToMany
    private List<Product> products;

    @NotNull
    private Date startDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
