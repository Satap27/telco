package it.polimi.telco.controllers;

import it.polimi.telco.model.ServicePackage;
import it.polimi.telco.model.Subscription;
import it.polimi.telco.model.User;
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

@WebServlet(
        name = "buyService",
        urlPatterns = {"/buyService"}
)
public class BuyServiceServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/ServicePackageService")
    private ServicePackageService servicePackageService;
    @EJB(name = "it.polimi.telco.services/SubscriptionService")
    private SubscriptionService subscriptionService;

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

        int servicePackageId;
        int validityPeriodId;
        int[] optionalProductsId = null;
        Date startDate;
        try {
            servicePackageId = Integer.parseInt(request.getParameter("service-package"));
            validityPeriodId = Integer.parseInt(request.getParameter("validity-period"));
            if (request.getParameterMap().containsKey("optional-products")) {
                optionalProductsId = Arrays.stream(request.getParameterValues("optional-products")).mapToInt(Integer::parseInt).toArray();
            }
            String startDateStr = request.getParameter("start-date");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDate = simpleDateFormat.parse(startDateStr);
            if (startDate == null) {
                throw new Exception("Missing starting date");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid values");
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        Subscription subscription =
                subscriptionService.createSubscription(servicePackageId, validityPeriodId, optionalProductsId, startDate, user);
        request.getSession().setAttribute("subscription", subscription);
        request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
    }
}
