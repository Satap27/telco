package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Entity @IdClass(PurchasePerPackagePeriodId.class)
@Table(name = "purchases_per_package_period", schema = "telco")
@NamedQuery(name = "PurchasePerPackagePeriod.getAll", query = "SELECT DISTINCT purchase FROM PurchasePerPackagePeriod purchase")
public class PurchasePerPackagePeriod {
    @Id
    @Column(name = "package_id")
    private long packageId;

    @Id
    @Column(name = "period_id")
    private long periodId;

    @NotNull
    private int months;

    @NotNull
    @Column(name = "monthly_fee")
    private double monthlyFee;

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

    public long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
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

class PurchasePerPackagePeriodId implements Serializable {
    protected long packageId;
    protected long periodId;

    public PurchasePerPackagePeriodId() {}

    public PurchasePerPackagePeriodId(long packageId, long periodId) {
        this.packageId = packageId;
        this.periodId = periodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasePerPackagePeriodId that = (PurchasePerPackagePeriodId) o;
        return packageId == that.packageId && periodId == that.periodId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageId, periodId);
    }
}
