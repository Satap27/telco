package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "revenues_per_package", schema = "telco")
@NamedQuery(name = "RevenuePerPackage.getAll", query = "SELECT DISTINCT revenues FROM RevenuePerPackage revenues")
public class RevenuePerPackage {
    @Id
    @Column(name = "package_id")
    private long packageId;

    @NotNull
    @Column(name = "package_name")
    private String packageName;

    @NotNull
    @Column(name = "with_options")
    private double revenuesWithOptions;

    @NotNull
    @Column(name = "without_options")
    private double revenuesWithoutOptions;

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

    public double getRevenuesWithOptions() {
        return revenuesWithOptions;
    }

    public void setRevenuesWithOptions(double revenuesWithOptions) {
        this.revenuesWithOptions = revenuesWithOptions;
    }

    public double getRevenuesWithoutOptions() {
        return revenuesWithoutOptions;
    }

    public void setRevenuesWithoutOptions(double revenuesWithoutOptions) {
        this.revenuesWithoutOptions = revenuesWithoutOptions;
    }
}
