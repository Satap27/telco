package it.polimi.telco.services;

import it.polimi.telco.model.ServiceEntry;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class ServiceEntryService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public ServiceEntryService() {
    }

    public ServiceEntry findServiceEntryById(long serviceEntryId) {
        return (em.find(ServiceEntry.class, serviceEntryId));
    }
}
