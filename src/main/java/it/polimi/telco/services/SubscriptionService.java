package it.polimi.telco.services;

import it.polimi.telco.model.Subscription;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class SubscriptionService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public SubscriptionService() {
    }

    public Subscription findSubscriptionById(long subscriptionId) {
        return (em.find(Subscription.class, subscriptionId));
    }
}
