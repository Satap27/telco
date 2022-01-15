package it.polimi.telco.services;

import it.polimi.telco.model.ServicePackage;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ServicePackageService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public ServicePackageService() {
    }

    public ServicePackage findServicePackageById(long servicePackageId) {
        return (em.find(ServicePackage.class, servicePackageId));
    }

    public List<ServicePackage> getAllServicePackages() throws Exception {
        try {
          return em.createNamedQuery("ServicePackage.getAllServicePackages",ServicePackage.class).getResultList();
        } catch (Exception e) {
            throw new Exception("Couldn't retrieve service packages");
        }
    }

    public List<ServicePackage> getAllServicePackagesDetailed() throws Exception {
        try {
          return em.createNamedQuery("ServicePackage.getAllServicePackagesDetailed",ServicePackage.class).getResultList();
        } catch (Exception e) {
            throw new Exception("Couldn't retrieve service packages");
        }
    }
}
