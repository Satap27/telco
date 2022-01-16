package it.polimi.telco.services;

import it.polimi.telco.model.Product;
import it.polimi.telco.model.ServicePackage;
import it.polimi.telco.model.TelcoService;
import it.polimi.telco.model.ValidityPeriod;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Stateless
public class ServicePackageService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public ServicePackageService() {
    }

    public ServicePackage findServicePackageById(long servicePackageId) {
        return (em.find(ServicePackage.class, servicePackageId));
    }

    public long createServicePackage(String name, long[] optionalProductsId, long[] validityPeriodsId, long[] servicesId) throws Exception {
        List<Product> products = new ArrayList<>();
        if (optionalProductsId != null) {
            for (long optionalProductId : optionalProductsId) {
                Product product = em.find(Product.class, optionalProductId);
                if (product == null) {
                    // TODO ok this exception?
                    throw new NoSuchElementException();
                }
                products.add(product);
            }
        }

        List<ValidityPeriod> validityPeriods = new ArrayList<>();
        for (long validityPeriodId : validityPeriodsId) {
            ValidityPeriod validityPeriod = em.find(ValidityPeriod.class, validityPeriodId);
            if (validityPeriod == null) {
                // TODO ok this exception?
                throw new NoSuchElementException();
            }
            validityPeriods.add(validityPeriod);
        }

        List<TelcoService> services = new ArrayList<>();
        for (long serviceId : servicesId) {
            TelcoService telcoService = em.find(TelcoService.class, serviceId);
            if (telcoService == null) {
                // TODO ok this exception?
                throw new NoSuchElementException();
            }
            services.add(telcoService);
        }
        ServicePackage servicePackage = new ServicePackage();
        servicePackage.setName(name);
        servicePackage.setAvailableOptionalProducts(products);
        servicePackage.setAvailableValidityPeriods(validityPeriods);
        servicePackage.setServices(services);
        em.persist(servicePackage);
        em.flush();
        return servicePackage.getId();
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
