package it.polimi.telco.services;

import it.polimi.telco.model.ValidityPeriod;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class ValidityPeriodService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public ValidityPeriodService() {
    }

    public ValidityPeriod findValidityPeriodById(long validityPeriodId) {
        return (em.find(ValidityPeriod.class, validityPeriodId));
    }
}
