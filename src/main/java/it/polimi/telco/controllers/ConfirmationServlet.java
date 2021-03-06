package it.polimi.telco.controllers;

import it.polimi.telco.exceptions.InvalidOrderException;
import it.polimi.telco.exceptions.InvalidSubscriptionException;
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
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No subscription retrieved");
            return;
        }
        request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        boolean fail = request.getParameterMap().containsKey("fail");
        Subscription subscription = (Subscription) request.getSession().getAttribute("subscription");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || subscription == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid values");
            return;
        }
        try {
            User refreshedUser = orderService.processOrder(subscription, user, fail);
            // if an exception is raised the subscription won't be removed from the session
            request.getSession().setAttribute("user", refreshedUser);
            request.getSession().removeAttribute("subscription");
            request.getSession().removeAttribute("totalPrice");
        } catch (InvalidOrderException | InvalidSubscriptionException e) {
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, e.getMessage());
            return;
        }
        String path = getServletContext().getContextPath() + "/homepage";
        response.sendRedirect(path);
    }
}
