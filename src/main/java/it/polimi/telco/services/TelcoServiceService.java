package it.polimi.telco.services;

import it.polimi.telco.model.TelcoService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class TelcoServiceService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public TelcoServiceService() {
    }

    public TelcoService findTelcoServiceById(long telcoServiceId) {
        return (em.find(TelcoService.class, telcoServiceId));
    }
}
