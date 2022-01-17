package it.polimi.telco.services;

import it.polimi.telco.model.ProductsPerPackage;
import it.polimi.telco.model.PurchasePerPackage;
import it.polimi.telco.model.PurchasePerPackagePeriod;
import it.polimi.telco.model.RevenuePerPackage;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class StatisticsService {
    @PersistenceContext(unitName = "TelcoEJB")
    private EntityManager em;

    public StatisticsService() {
    }

    public List<PurchasePerPackage> getAllPurchasesPerPackage() throws Exception {
        try {
            return em.createNamedQuery("PurchasePerPackage.getAll", PurchasePerPackage.class).getResultList();
        } catch (Exception e) {
            throw new Exception("Couldn't retrieve purchases per package");
        }
    }

    public List<PurchasePerPackagePeriod> getAllPurchasesPerPackagePeriod() throws Exception {
        try {
            return em.createNamedQuery("PurchasePerPackagePeriod.getAll", PurchasePerPackagePeriod.class).getResultList();
        } catch (Exception e) {
            throw new Exception("Couldn't retrieve purchases per package and period");
        }
    }

    public List<RevenuePerPackage> getAllRevenuesPerPackage() throws Exception {
        try {
            return em.createNamedQuery("RevenuePerPackage.getAll", RevenuePerPackage.class).getResultList();
        } catch (Exception e) {
            throw new Exception("Couldn't retrieve revenues per package");
        }
    }

    public List<ProductsPerPackage> getAllProductsPerPackage() throws Exception {
        try {
            return em.createNamedQuery("ProductsPerPackage.getAll", ProductsPerPackage.class).getResultList();
        } catch (Exception e) {
            throw new Exception("Couldn't retrieve average products per package");
        }
    }
}
