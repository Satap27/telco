package it.polimi.telco.controllers;

import it.polimi.telco.model.PurchasePerPackage;
import it.polimi.telco.model.PurchasePerPackagePeriod;
import it.polimi.telco.model.RevenuePerPackage;
import it.polimi.telco.services.StatisticsService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "salesReport",
        urlPatterns = {"/salesReport"}
)
public class SalesReportServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/StatisticsService")
    private StatisticsService statisticsService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<PurchasePerPackage>  purchasesPerPackage = null;
        List<PurchasePerPackagePeriod>  purchasesPerPackagePeriod = null;
        List<RevenuePerPackage>  revenuesPerPackage = null;
        try {
            purchasesPerPackage = statisticsService.getAllPurchasesPerPackage();
            purchasesPerPackagePeriod = statisticsService.getAllPurchasesPerPackagePeriod();
            revenuesPerPackage = statisticsService.getAllRevenuesPerPackage();
        } catch (Exception e) {
            // TODO throw
            e.printStackTrace();
        }
        request.setAttribute("purchasesPerPackage", purchasesPerPackage);
        request.setAttribute("purchasesPerPackagePeriod", purchasesPerPackagePeriod);
        request.setAttribute("revenuesPerPackage", revenuesPerPackage);
        request.getRequestDispatcher("/salesReport.jsp").forward(request, response);
    }
}
