package it.polimi.telco.controllers;

import it.polimi.telco.model.Subscription;
import it.polimi.telco.model.User;
import it.polimi.telco.services.OrderService;
import it.polimi.telco.services.SubscriptionService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(
        name = "confirmation",
        urlPatterns = {"/confirmation"}
)
public class ConfirmationServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/SubscriptionService")
    private SubscriptionService subscriptionService;
    @EJB(name = "it.polimi.telco.services/OrderService")
    private OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Subscription subscription = (Subscription) request.getSession().getAttribute("subscription");
        if (subscription == null) {
            // TODO what should i do in this case?
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "TODO");
            return;
        }
        request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Subscription subscription = (Subscription) request.getSession().getAttribute("subscription");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || subscription == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid values");
            return;
        }
        subscription.setUser(user);
        try {
            subscriptionService.saveSubscription(subscription);
            request.getSession().removeAttribute("subscription");
            orderService.createOrderFromSubscription(subscription);
            // TODO than should mark as valid or invalid
        } catch (Exception e) {
           // TODO rollback and restore session?
        }
        String path = getServletContext().getContextPath() + "/homepage";
        response.sendRedirect(path);
    }
}
