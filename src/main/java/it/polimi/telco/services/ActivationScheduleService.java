package it.polimi.telco.services;

import it.polimi.telco.model.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.List;

@Stateless
public class ActivationScheduleService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public ActivationScheduleService(){
    }

    public void createServiceActivationRecordForOrder(Order order) {
        List<TelcoService> services = order.getServicePackage().getServices();
        List<Product> products = order.getProducts();
        Date activationDate = order.getStartDate();
        Date deactivationDate = DateUtils.addMonths(activationDate, order.getValidityPeriod().getNumberOfMonths());
        User user = order.getUser();

        ActivationSchedule activationSchedule = new ActivationSchedule();
        activationSchedule.setDateOfActivation(activationDate);
        activationSchedule.setDateOfDeactivation(deactivationDate);
        activationSchedule.setServices(services);
        activationSchedule.setOptionalProducts(products);
        activationSchedule.setUser(user);
        em.persist(activationSchedule);
    }

}
