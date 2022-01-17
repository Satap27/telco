package it.polimi.telco.services;

import it.polimi.telco.model.Product;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Stateless
public class ProductService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public ProductService() {
    }

    public long createProduct(String name, double monthlyFee) {
        Product product = new Product();
        product.setName(name);
        product.setMonthlyFee(monthlyFee);
        em.persist(product);
        em.flush();
        return product.getId();
    }

    public List<Product> getAllProducts() throws Exception {
        try {
            return em.createNamedQuery("Product.getAllProducts",Product.class).getResultList();
        } catch (Exception e) {
            throw new Exception("Couldn't retrieve products");
        }
    }

    public Product findProductById(long productId) {
        return (em.find(Product.class, productId));
    }

    public List<Product> findProductsById (long[] productsId) throws NoSuchElementException{
        List<Product> products = new ArrayList<>();
        if (productsId != null) {
            for (long productId : productsId) {
                Product product = findProductById(productId);
                if (product == null) {
                    throw new NoSuchElementException("Product not found");
                }
                products.add(product);
            }
        }
        return products;
    }
}
