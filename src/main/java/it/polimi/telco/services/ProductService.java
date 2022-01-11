package it.polimi.telco.services;

import it.polimi.telco.model.Product;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class ProductService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public ProductService() {
    }

    public Product findProductById(long productId) {
        return (em.find(Product.class, productId));
    }
}
