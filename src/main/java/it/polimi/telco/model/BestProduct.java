package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "best_product", schema = "telco")
@NamedQuery(name = "BestProduct.getBestProduct", query = "SELECT DISTINCT product FROM BestProduct product ORDER BY product.value DESC")
public class BestProduct {
    @Id
    @Column(name = "product_id")
    private long productId;

    @NotNull
    @Column(name = "product_name")
    private String productName;

    @NotNull
    private double value;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
