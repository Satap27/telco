package it.polimi.telco.services;

import it.polimi.telco.model.ServicePackage;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class ServicePackageService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public ServicePackageService() {
    }

    public ServicePackage findServicePackageById(long servicePackageId) {
        return (em.find(ServicePackage.class, servicePackageId));
    }
}
