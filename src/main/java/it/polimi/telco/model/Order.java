package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order", schema = "telco")
@NamedQuery(name = "Order.getRejectedOrdersForUserId", query = "SELECT DISTINCT o FROM Order o" +
        " LEFT JOIN FETCH o.user WHERE o.user.id = ?1 AND o.valid=false")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "creation_date")
    private Date creationDate;

    @NotNull
    @OneToOne
    @JoinColumn(name = "fk_id_user")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_id_package")
    private ServicePackage servicePackage;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_id_period")
    private ValidityPeriod validityPeriod;

    @ManyToMany
    @JoinTable(name = "order_product", schema = "telco", joinColumns = @JoinColumn(name = "fk_id_order"), inverseJoinColumns = @JoinColumn(name = "fk_id_product"))
    private List<Product> products;

    @NotNull
    @Column(name = "total_price")
    private double totalPrice;

    @NotNull
    @Column(name = "start_date")
    private Date startDate;

    @NotNull
    private boolean valid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
