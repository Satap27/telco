package it.polimi.telco.controllers;

import it.polimi.telco.exceptions.CredentialsException;
import it.polimi.telco.model.Subscription;
import it.polimi.telco.model.User;
import it.polimi.telco.services.UserService;
import jakarta.ejb.EJB;
import jakarta.persistence.NonUniqueResultException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

@WebServlet(
        name = "loginPage",
        urlPatterns = {"/loginPage"}
)
public class LoginServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/UserService")
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username;
        String password;
        try {
            username = StringEscapeUtils.escapeJava(request.getParameter("username"));
            password = StringEscapeUtils.escapeJava(request.getParameter("password"));
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                throw new Exception("Missing or empty credential value");
            }

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
            return;
        }
        User user;
        try {
            user = userService.checkCredentials(username, password);
        } catch (CredentialsException | NonUniqueResultException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
            return;
        }

        if (user == null) {
            request.setAttribute("errorMsg", "Incorrect username or password");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("user", user);
            Subscription subscription = (Subscription) request.getSession().getAttribute("subscription");
            String path = getServletContext().getContextPath();
            path += subscription == null ? "/homepage" : "/confirmation";
            response.sendRedirect(path);
        }
    }
}
