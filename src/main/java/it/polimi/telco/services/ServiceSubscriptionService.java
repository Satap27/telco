package it.polimi.telco.services;

import it.polimi.telco.model.ServiceSubscription;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class ServiceSubscriptionService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public ServiceSubscriptionService() {
    }

    public ServiceSubscription findServiceSubscriptionById(long serviceSubscriptionId) {
        return (em.find(ServiceSubscription.class, serviceSubscriptionId));
    }
}
