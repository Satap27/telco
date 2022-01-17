package it.polimi.telco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "validity_period", schema = "telco")
public class ValidityPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "months")
    private int numberOfMonths;

    @NotNull
    @Column(name = "monthly_fee")
    private double monthlyFee;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_id_package")
    private ServicePackage servicePackage;

    public ValidityPeriod(int numberOfMonths, double monthlyFee) {
        this.numberOfMonths = numberOfMonths;
        this.monthlyFee = monthlyFee;
    }

    public ValidityPeriod() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getNumberOfMonths() {
        return numberOfMonths;
    }

    public void setNumberOfMonths(int numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }
}
