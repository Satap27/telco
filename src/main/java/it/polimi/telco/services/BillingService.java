package it.polimi.telco.services;

import it.polimi.telco.model.Order;
import jakarta.ejb.Stateless;

@Stateless
public class BillingService {
    public boolean billOrder(Order order, boolean fail) {
        boolean successfulBilling;
        successfulBilling = mockBillingService(fail);
        return successfulBilling;
    }

    private boolean mockBillingService(boolean fail) {
        return !fail;
    }
}

