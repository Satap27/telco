package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products_per_package", schema = "telco")
@NamedQuery(name = "ProductsPerPackage.getAll", query = "SELECT DISTINCT products FROM ProductsPerPackage products")
public class ProductsPerPackage {
    @Id
    @Column(name = "package_id")
    private long packageId;

    @NotNull
    @Column(name = "package_name")
    private String packageName;

    @NotNull
    @Column(name = "products_number")
    private double productsAverage;

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public double getProductsAverage() {
        return productsAverage;
    }

    public void setProductsAverage(double productsAverage) {
        this.productsAverage = productsAverage;
    }
}
