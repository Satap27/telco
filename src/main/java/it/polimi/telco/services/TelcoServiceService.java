package it.polimi.telco.services;

import it.polimi.telco.model.ServiceEntry;
import it.polimi.telco.model.TelcoService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Stateless
public class TelcoServiceService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public TelcoServiceService() {
    }

    public long createTelcoService(String type, long[] entriesId) {
        List<ServiceEntry> serviceEntries = new ArrayList<>();
        if (entriesId != null) {
            for (long entryId : entriesId) {
                ServiceEntry serviceEntry = em.find(ServiceEntry.class, entryId);
                if (serviceEntry == null) {
                    // TODO ok this exception?
                    throw new NoSuchElementException();
                }
                serviceEntries.add(serviceEntry);
            }
        }
        TelcoService telcoService = new TelcoService();
        telcoService.setType(type);
        telcoService.setServiceEntries(serviceEntries);
        em.persist(telcoService);
        em.flush();
        return telcoService.getId();
    }

    public TelcoService findTelcoServiceById(long telcoServiceId) {
        return (em.find(TelcoService.class, telcoServiceId));
    }
}
