package it.polimi.telco.services;

import it.polimi.telco.exceptions.InvalidOrderException;
import it.polimi.telco.exceptions.InvalidSubscriptionException;
import it.polimi.telco.model.*;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.Date;
import java.util.List;

@Stateless
public class OrderService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;
    @EJB(name = "it.polimi.telco.services/BillingService")
    BillingService billingService;
    @EJB(name = "it.polimi.telco.services/PriceCalculationService")
    private PriceCalculationService calculationService;

    public OrderService() {
    }

    public void processOrder(Subscription subscription, User user) throws InvalidOrderException, InvalidSubscriptionException {
        // Inserting both subscription and order into the database inside the same transaction
        try {
            subscription.setUser(user);
            Order order = createOrderFromSubscription(subscription);
            if (billingService.billOrder(order)) {
                order.setValid(true);
            } else {
                //set user insolvent
            }
            em.persist(subscription);
            em.persist(order);
            em.flush();
        }
        catch (PersistenceException e) {
            throw new InvalidOrderException("Invalid order or subscription");
        }
    }

    public Order createOrderFromSubscription(Subscription subscription) throws InvalidSubscriptionException {
        if (subscription == null) {
            throw new InvalidSubscriptionException("Subscription object is null");
        }
        Order order = new Order();
        order.setCreationDate(new Date());
        order.setValid(false);
        populateOrderFromSubscription(subscription, order);
        return order;
    }

    protected void populateOrderFromSubscription(Subscription subscription, Order order) throws InvalidSubscriptionException {
        User user = subscription.getUser();
        ServicePackage servicePackage = subscription.getServicePackage();
        ValidityPeriod validityPeriod = subscription.getValidityPeriod();
        List<Product> optionalProducts = subscription.getProducts();
        Date subscriptionStartDate = subscription.getStartDate();

        if (user == null || servicePackage == null || validityPeriod == null || optionalProducts == null || subscriptionStartDate == null) {
            throw new InvalidSubscriptionException("The subscription object used for creating the order is invalid");
        }

        order.setUser(user);
        order.setServicePackage(servicePackage);
        order.setStartDate(subscriptionStartDate);
        order.setProducts(optionalProducts);
        order.setValidityPeriod(validityPeriod);
        order.setTotalPrice(calculationService.calculateSubscriptionTotalPrice(subscription));
    }
}
