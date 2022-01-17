package it.polimi.telco.controllers;

import it.polimi.telco.model.Order;
import it.polimi.telco.model.User;
import it.polimi.telco.services.OrderService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(
        name = "restoreConfirmation",
        urlPatterns = {"/restoreConfirmation"}
)
public class RestoreConfirmationServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/OrderService")
    private OrderService orderService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long orderId;
        try {
            orderId = Long.parseLong(request.getParameter("order-id"));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid values");
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthenticated");
            return;
        }

        Order order = orderService.findOrderById(orderId);
        if (order == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
            return;
        }
        if (order.getUser().getId() != user.getId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't own this order");
            return;
        }
        request.getSession().setAttribute("totalPrice", order.getTotalPrice());
        request.getSession().setAttribute("subscription", order.getSubscription());
        request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
    }
}
