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

    public long createValidityPeriod(int months, double fee) {
        ValidityPeriod validityPeriod = new ValidityPeriod();
        validityPeriod.setNumberOfMonths(months);
        validityPeriod.setMonthlyFee(fee);
        em.persist(validityPeriod);
        em.flush();
        return validityPeriod.getId();
    }

    public ValidityPeriod findValidityPeriodById(long validityPeriodId) {
        return (em.find(ValidityPeriod.class, validityPeriodId));
    }
}
