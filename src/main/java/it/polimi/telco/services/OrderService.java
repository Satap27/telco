package it.polimi.telco.services;

import it.polimi.telco.model.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;
import java.util.List;

@Stateless
public class OrderService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public OrderService() {
    }

    public long saveOrder(Order order){
        em.persist(order);
        em.flush();
        return order.getId();
    }

    public void createOrderFromSubscription(Subscription subscription) throws Exception {
        if (subscription != null) {
            Order order = new Order();
            order.setCreationDate(new Date());
            order.setValid(false);
            populateOrderFromSubscription(subscription, order);
            saveOrder(order);
        }
    }

    private void populateOrderFromSubscription(Subscription subscription, Order order) throws Exception {
        User user = subscription.getUser();
        ServicePackage servicePackage = subscription.getServicePackage();
        ValidityPeriod validityPeriod = subscription.getValidityPeriod();
        List<Product> optionalProducts = subscription.getProducts();
        Date subscriptionStartDate = subscription.getStartDate();

        if(user == null || servicePackage == null || validityPeriod == null || optionalProducts == null || subscriptionStartDate == null){
            throw new Exception("The subscription object used for creating the order is invalid");
        }

        order.setUser(user);
        order.setServicePackage(servicePackage);
        order.setStartDate(subscriptionStartDate);
        order.setProducts(optionalProducts);
        order.setValidityPeriod(validityPeriod);
        order.setTotalPrice(100); //totalPrice calculate from service
    }
}
