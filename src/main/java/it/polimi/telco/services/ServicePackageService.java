package it.polimi.telco.services;

import it.polimi.telco.exceptions.InvalidServicePackageException;
import it.polimi.telco.model.Product;
import it.polimi.telco.model.ServicePackage;
import it.polimi.telco.model.TelcoService;
import it.polimi.telco.model.ValidityPeriod;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

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

    public long createServicePackage(String name, List<Product> optionalProducts, List<ValidityPeriod> validityPeriods, List<TelcoService> services) throws InvalidServicePackageException {
        try {
            ServicePackage servicePackage = new ServicePackage();
            servicePackage.setName(name);
            servicePackage.setAvailableOptionalProducts(optionalProducts);
            servicePackage.setAvailableValidityPeriods(validityPeriods); // updates both sides of the relationship
            servicePackage.setServices(services); // updates both sides of the relationship
            em.persist(servicePackage); // all the other objects are also persisted via cascading
            em.flush();
            return servicePackage.getId();
        }
        catch (PersistenceException e) {
            e.printStackTrace();
            throw new InvalidServicePackageException("Invalid service package");
        }
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
