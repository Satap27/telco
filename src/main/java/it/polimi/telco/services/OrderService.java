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
    @EJB(name = "it.polimi.telco.services/ActivationScheduleService")
    private ActivationScheduleService activationScheduleService;
    @EJB(name = "it.polimi.telco.services/AlertService")
    private AlertService alertService;

    public OrderService() {
    }

    public List<Order> getRejectedOrdersForInsolventUser(User user){
        if(!user.isInsolvent()){
            return null;
        }
        List<Order> invalidOrder;
        try {
            invalidOrder = em.createNamedQuery("Order.getRejectedOrdersForUserId", Order.class).setParameter(1, user.getId())
                    .getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Couldn't retrieve the invalid orders");
        }
        return invalidOrder;
    }

    public User processOrder(Subscription subscription, User user) throws InvalidOrderException, InvalidSubscriptionException {
        // Inserting both subscription and order into the database inside the same transaction
        User refreshedUser;
        try {
            subscription.setUser(user);
            Order existingOrder;
            try {
                existingOrder = em.createNamedQuery("Order.getFromSubscription", Order.class).setParameter(1, subscription.getId()).getSingleResult();
                refreshedUser = billingOrder(existingOrder);
                em.merge(existingOrder);
            }  catch (PersistenceException e) {
                existingOrder = createOrderFromSubscription(subscription);
                refreshedUser = billingOrder(existingOrder);
                em.persist(subscription);
                em.persist(existingOrder);
            }
            em.flush();
        }
        catch (PersistenceException e) {
            throw new InvalidOrderException("Invalid order or subscription");
        }
        return refreshedUser;
    }

    public Order createOrderFromSubscription(Subscription subscription) throws InvalidSubscriptionException {
        if (subscription == null) {
            throw new InvalidSubscriptionException("Subscription object is null");
        }
        Order order = new Order();
        order.setCreationDate(new Date());
        order.setValid(false);
        order.setSubscription(subscription);
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

    private User billingOrder(Order order) {
        User user = order.getUser();
        if (billingService.billOrder(order)) {
            order.setValid(true);
            activationScheduleService.createServiceActivationRecordForOrder(order);
            if (getRejectedOrdersForInsolventUser(user).isEmpty())
                user.setInsolvent(false);
        } else {
            int failedPayments = user.getFailedPayments();
            user.setInsolvent(true);
            user.setFailedPayments(failedPayments + 1);
            if (failedPayments == 2) {
                alertService.createAlert(user, order.getTotalPrice());
            }
        }
        em.merge(user);
        return user;
    }

    public Order findOrderById(long orderId) {
        return (em.find(Order.class, orderId));
    }
}
