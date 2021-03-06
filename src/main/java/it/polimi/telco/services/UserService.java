package it.polimi.telco.services;

import it.polimi.telco.exceptions.CredentialsException;
import it.polimi.telco.exceptions.ExistingUserException;
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

    public User findUserById(long userId) {
        return (em.find(User.class, userId));
    }

    public long createUser(String username, String password, String email, String role) throws ExistingUserException {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setRole(role);
            user.setInsolvent(false);
            user.setFailedPayments(0);
            em.persist(user);
            em.flush();
            return user.getId();
        }
        catch (PersistenceException e) {
            throw new ExistingUserException("Username already occurs in the database");
        }
    }

    public User checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
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
            return userList.get(0);
        }
        throw new NonUniqueResultException("More than one user registered with same credentials");

    }

    public List<User> getAllInsolventUsers() throws Exception {
        try {
            return em.createNamedQuery("User.getAllInsolvents", User.class).getResultList();
        } catch (Exception e) {
            throw new Exception("Couldn't retrieve insolvent users");
        }
    }

}
