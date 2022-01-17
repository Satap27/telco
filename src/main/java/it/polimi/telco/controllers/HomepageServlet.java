package it.polimi.telco.controllers;

import it.polimi.telco.model.Order;
import it.polimi.telco.model.ServicePackage;
import it.polimi.telco.model.User;
import it.polimi.telco.services.OrderService;
import it.polimi.telco.services.ServicePackageService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "homepage",
        urlPatterns = {"/homepage"}
)
public class HomepageServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/ServicePackageService")
    private ServicePackageService servicePackageService;
    @EJB(name = "it.polimi.telco.services/OrderService")
    private OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        populateRequestWithServicePackages(request);
        populateRequestWithRejectedOrders(request);
        request.getRequestDispatcher("/homepage.jsp").forward(request, response);
    }

    private void populateRequestWithServicePackages(HttpServletRequest request) {
        List<ServicePackage> servicePackages = null;
        try {
            servicePackages = servicePackageService.getAllServicePackages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("servicePackages", servicePackages);
    }

    private void populateRequestWithRejectedOrders(HttpServletRequest request) {
        User sessionUser = (User) request.getSession().getAttribute("user");
        if (sessionUser != null) {
            List<Order> rejectedOrders = orderService.getRejectedOrdersForInsolventUser(sessionUser);
            request.setAttribute("rejectedOrders", rejectedOrders);
        }
    }
}
