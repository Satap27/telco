package it.polimi.telco.services;

import it.polimi.telco.exceptions.InvalidSubscription;
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

    private void checkConstraints(ServicePackage servicePackage, ValidityPeriod validityPeriod, List<Product> productList) throws InvalidSubscription {
        if (!servicePackage.getAvailableValidityPeriods().contains(validityPeriod))
            throw new InvalidSubscription("Invalid validity period for the selected service package");
        for (Product product : productList) {
            if (!servicePackage.getAvailableOptionalProducts().contains(product))
                throw new InvalidSubscription("Invalid optional product for the selected service package");
        }
    }

    public Subscription createSubscription(int servicePackageId, int validityPeriodId, int[] optionalProductsId, Date startDate, User user) throws InvalidSubscription, NoSuchElementException {
        ServicePackage servicePackage = em.find(ServicePackage.class, servicePackageId);
        ValidityPeriod validityPeriod = em.find(ValidityPeriod.class, validityPeriodId);
        if (servicePackage == null || validityPeriod == null)
            throw new NoSuchElementException();
        List<Product> productList = new ArrayList<>();
        if (optionalProductsId != null) {
            for (long optionalProductId : optionalProductsId) {
                Product product = em.find(Product.class, optionalProductId);
                if (product == null) {
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

    public Subscription findSubscriptionById(long subscriptionId) {
        return (em.find(Subscription.class, subscriptionId));
    }
}
