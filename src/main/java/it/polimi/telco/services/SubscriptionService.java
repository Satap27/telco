package it.polimi.telco.services;

import it.polimi.telco.model.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Stateless
public class SubscriptionService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public SubscriptionService() {
    }

    public Subscription createSubscription(int servicePackageId, int validityPeriodId, int[] optionalProductsId, Date startDate, User user) {
        ServicePackage servicePackage = em.find(ServicePackage.class, servicePackageId);
        ValidityPeriod validityPeriod = em.find(ValidityPeriod.class, validityPeriodId);
        if (servicePackage == null || validityPeriod == null)
            // TODO ok this exception?
            throw new NoSuchElementException();
        List<Product> productList = new ArrayList<>();
        if (optionalProductsId != null) {
            for (int optionalProductId : optionalProductsId) {
                Product product = em.find(Product.class, optionalProductId);
                if (product == null) {
                    // TODO ok this exception?
                    throw new NoSuchElementException();
                }
                productList.add(product);
            }
        }

        Subscription subscription = new Subscription();
        subscription.setServicePackage(servicePackage);
        subscription.setValidityPeriod(validityPeriod);
        subscription.setProducts(productList);
        if (user != null)
            subscription.setUser(user);
        subscription.setStartDate(startDate);
        return subscription;
    }

    public long saveSubscription(Subscription subscription) {
        em.persist(subscription);
        em.flush();
        return subscription.getId();
    }

    public Subscription findSubscriptionById(long subscriptionId) {
        return (em.find(Subscription.class, subscriptionId));
    }
}
