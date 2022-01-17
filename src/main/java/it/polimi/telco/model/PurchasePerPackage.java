package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "purchases_per_package", schema = "telco")
@NamedQuery(name = "PurchasePerPackage.getAll", query = "SELECT DISTINCT purchase FROM PurchasePerPackage purchase")
public class PurchasePerPackage {
    @Id
    @Column(name = "package_id")
    private long packageId;

    @NotNull
    @Column(name = "package_name")
    private String packageName;

    @NotNull
    private int purchases;

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

    public int getPurchases() {
        return purchases;
    }

    public void setPurchases(int purchases) {
        this.purchases = purchases;
    }
}
