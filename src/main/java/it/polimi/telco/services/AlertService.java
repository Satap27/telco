package it.polimi.telco.services;

import it.polimi.telco.model.Alert;
import it.polimi.telco.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;

@Stateless
public class AlertService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public AlertService(){
    }

    public void createAlert(User user, double amount) {
        Alert alert = new Alert();
        alert.setUser(user);
        alert.setAmount(amount);
        alert.setRejectionTime(new Date());
        em.persist(alert);
    }

}
