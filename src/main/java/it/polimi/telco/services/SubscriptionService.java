package it.polimi.telco.services;

import it.polimi.telco.exceptions.InvalidSubscriptionException;
import it.polimi.telco.model.*;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.*;

@Stateless
public class SubscriptionService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;
    @EJB(name = "it.polimi.telco.services/ProductService")
    private ProductService productService;

    public SubscriptionService() {
    }

    private void checkConstraints(ServicePackage servicePackage, ValidityPeriod validityPeriod, List<Product> productList) throws InvalidSubscriptionException {
        if (!servicePackage.getAvailableValidityPeriods().contains(validityPeriod))
            throw new InvalidSubscriptionException("Invalid validity period for the selected service package");
        for (Product product : productList) {
            if (!servicePackage.getAvailableOptionalProducts().contains(product))
                throw new InvalidSubscriptionException("Invalid optional product for the selected service package");
        }
    }

    public Subscription createSubscription(long servicePackageId, long validityPeriodId, long[] optionalProductsId, Date startDate, User user) throws InvalidSubscriptionException, NoSuchElementException {
        ServicePackage servicePackage = em.find(ServicePackage.class, servicePackageId);
        ValidityPeriod validityPeriod = em.find(ValidityPeriod.class, validityPeriodId);
        if (servicePackage == null || validityPeriod == null)
            throw new NoSuchElementException();
        List<Product> products = productService.findProductsById(optionalProductsId);
        checkConstraints(servicePackage, validityPeriod, products);

        Subscription subscription = new Subscription();
        subscription.setServicePackage(servicePackage);
        subscription.setValidityPeriod(validityPeriod);
        subscription.setProducts(products);
        if (user != null)
            subscription.setUser(user);
        subscription.setStartDate(startDate);
        return subscription;
    }

    public Subscription findSubscriptionById(long subscriptionId) {
        return (em.find(Subscription.class, subscriptionId));
    }
}
