package it.polimi.telco.services;

import it.polimi.telco.exceptions.InvalidSubscription;
import it.polimi.telco.model.Product;
import it.polimi.telco.model.Subscription;
import it.polimi.telco.model.ValidityPeriod;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class PriceCalculationService {

    public PriceCalculationService() {
    }

    public double calculateSubscriptionTotalPrice(Subscription subscription) throws InvalidSubscription {
        double monthlyFee;
        int nrOfMonths;
        ValidityPeriod subscriptionValidityPeriod = subscription.getValidityPeriod();
        if (subscriptionValidityPeriod == null) {
            throw new InvalidSubscription("Null validity period in subscription");
        }
        monthlyFee = subscriptionValidityPeriod.getMonthlyFee();
        nrOfMonths = subscriptionValidityPeriod.getNumberOfMonths();

        List<Double> optionalProductsMonthlyFees = getListOfFeesFromListOfOptionalProducts(subscription);
        return calculateTotalPrice(monthlyFee, nrOfMonths, optionalProductsMonthlyFees);
    }

    public double calculateTotalPrice(double monthlyFee, int nrOfMonths, List<Double> optionalProductsMonthlyFees) {
        double totalPrice = monthlyFee * nrOfMonths;
        double optionalProductsCost = getOptionalProductsFeesSum(optionalProductsMonthlyFees) * nrOfMonths;
        totalPrice += optionalProductsCost;
        return totalPrice;
    }

    protected List<Double> getListOfFeesFromListOfOptionalProducts(Subscription subscription) {
        List<Double> optionalProductsMonthlyFees = new ArrayList<>();
        if (subscription.getProducts() != null && !subscription.getProducts().isEmpty()) {
            optionalProductsMonthlyFees = subscription.getProducts().stream()
                    .map(Product::getMonthlyFee)
                    .collect(Collectors.toList());
        }
        return optionalProductsMonthlyFees;
    }


    private double getOptionalProductsFeesSum(List<Double> optionalProductsMonthlyFees) {
        double optionalProductsFeesSum = 0;
        if (!optionalProductsMonthlyFees.isEmpty()) {
            optionalProductsFeesSum = optionalProductsMonthlyFees.stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();
        }
        return optionalProductsFeesSum;
    }
}
