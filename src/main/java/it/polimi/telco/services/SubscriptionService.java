package it.polimi.telco.services;

import it.polimi.telco.model.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.*;

@Stateless
public class SubscriptionService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public SubscriptionService() {
    }

    private void checkConstraints(ServicePackage servicePackage, ValidityPeriod validityPeriod, List<Product> productList) throws Exception {
        if (!servicePackage.getAvailableValidityPeriods().contains(validityPeriod))
            // TODO better exception
            throw new Exception("");
        for (Product product : productList) {
            if (!servicePackage.getAvailableOptionalProducts().contains(product))
                // TODO better exception
                throw new Exception("");
        }
    }

    public Subscription createSubscription(int servicePackageId, int validityPeriodId, int[] optionalProductsId, Date startDate, User user) throws Exception {
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
        checkConstraints(servicePackage, validityPeriod, productList);

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
