package it.polimi.telco.controllers;

import it.polimi.telco.exceptions.InvalidSubscriptionException;
import it.polimi.telco.model.ServicePackage;
import it.polimi.telco.model.Subscription;

import it.polimi.telco.model.User;
import it.polimi.telco.services.PriceCalculationService;
import it.polimi.telco.services.ServicePackageService;
import it.polimi.telco.services.SubscriptionService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@WebServlet(
        name = "buyService",
        urlPatterns = {"/buyService"}
)
public class BuyServiceServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/ServicePackageService")
    private ServicePackageService servicePackageService;
    @EJB(name = "it.polimi.telco.services/SubscriptionService")
    private SubscriptionService subscriptionService;
    @EJB(name = "it.polimi.telco.services/PriceCalculationService")
    private PriceCalculationService calculationService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ServicePackage> servicePackages = null;
        try {
            servicePackages = servicePackageService.getAllServicePackagesDetailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("servicePackages", servicePackages);
        request.getRequestDispatcher("/buyService.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long servicePackageId;
        long validityPeriodId;
        long[] optionalProductsId = null;
        Date startDate;
        try {
            servicePackageId = Integer.parseInt(request.getParameter("service-package"));
            validityPeriodId = Integer.parseInt(request.getParameter("validity-period"));
            if (request.getParameterMap().containsKey("optional-products")) {
                optionalProductsId = Arrays.stream(request.getParameterValues("optional-products")).mapToLong(Long::parseLong).toArray();
            }
            String startDateStr = request.getParameter("start-date");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDate = simpleDateFormat.parse(startDateStr);
            if (startDate == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing starting date");
                return;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid values");
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        Subscription subscription;
        double totalPrice = 0;

        try {
            subscription = subscriptionService.createSubscription(servicePackageId, validityPeriodId, optionalProductsId, startDate, user);
        } catch (InvalidSubscriptionException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Service package constraint are not met: " + e.getMessage());
            return;
        } catch (NoSuchElementException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid subscription paramenters");
            return;
        }
        try {
            totalPrice = calculationService.calculateSubscriptionTotalPrice(subscription);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Subscription");
        }
        request.getSession().setAttribute("totalPrice", totalPrice);
        request.getSession().setAttribute("subscription", subscription);
        request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
    }
}
