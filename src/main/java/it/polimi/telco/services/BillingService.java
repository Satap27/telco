package it.polimi.telco.services;

import it.polimi.telco.model.Order;
import jakarta.ejb.Stateless;

@Stateless
public class BillingService {
    public boolean billOrder(Order order) {
        boolean successfulBilling;
        successfulBilling = mockBillingService(order);
        return successfulBilling;
    }

    private boolean mockBillingService(Order order) {
        return ((int) (order.getTotalPrice())) % 2 == 0;
    }
}

