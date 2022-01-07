package it.polimi.telco.services;

import it.polimi.telco.exceptions.CredentialsException;
import it.polimi.telco.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public UserService() {
    }

    public User findById(int userId) {
        return (em.find(User.class, userId));
    }

    public Long checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
        List<User> userList;
        try {
            userList = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, username)
                    .setParameter(2, password).getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentials");
        }
        if (userList.isEmpty())
            return null;
        else if (userList.size() == 1) {
            User found = userList.get(0);
            return found.getId();
        }
        throw new NonUniqueResultException("More than one user registered with same credentials");

    }

}
