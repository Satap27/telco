package it.polimi.telco.controllers;

import it.polimi.telco.model.*;
import it.polimi.telco.services.AlertService;
import it.polimi.telco.services.OrderService;
import it.polimi.telco.services.StatisticsService;
import it.polimi.telco.services.UserService;
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
    @EJB(name = "it.polimi.telco.services/UserService")
    private UserService userService;
    @EJB(name = "it.polimi.telco.services/OrderService")
    private OrderService orderService;
    @EJB(name = "it.polimi.telco.services/AlertService")
    private AlertService alertService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<PurchasePerPackage>  purchasesPerPackage;
        List<PurchasePerPackagePeriod>  purchasesPerPackagePeriod;
        List<RevenuePerPackage>  revenuesPerPackage;
        List<ProductsPerPackage>  productsPerPackage;
        List<User>  insolventUsers;
        List<Order>  suspendedOrders;
        List<Alert> alerts;
        BestProduct bestProduct;
        try {
            purchasesPerPackage = statisticsService.getAllPurchasesPerPackage();
            purchasesPerPackagePeriod = statisticsService.getAllPurchasesPerPackagePeriod();
            revenuesPerPackage = statisticsService.getAllRevenuesPerPackage();
            productsPerPackage = statisticsService.getAllProductsPerPackage();
            insolventUsers = userService.getAllInsolventUsers();
            suspendedOrders = orderService.getAllSuspendedOrders();
            alerts = alertService.getAllAlerts();
            bestProduct = statisticsService.getBestProduct();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error retrieving values");
            return;
        }
        request.setAttribute("purchasesPerPackage", purchasesPerPackage);
        request.setAttribute("purchasesPerPackagePeriod", purchasesPerPackagePeriod);
        request.setAttribute("revenuesPerPackage", revenuesPerPackage);
        request.setAttribute("productsPerPackage", productsPerPackage);
        request.setAttribute("insolventUsers", insolventUsers);
        request.setAttribute("suspendedOrders", suspendedOrders);
        request.setAttribute("alerts", alerts);
        request.setAttribute("bestProduct", bestProduct);
        request.getRequestDispatcher("/salesReport.jsp").forward(request, response);
    }
}
