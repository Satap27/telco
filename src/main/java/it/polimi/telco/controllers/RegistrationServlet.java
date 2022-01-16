package it.polimi.telco.controllers;

import it.polimi.telco.services.UserService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

@WebServlet(
        name = "registrationPage",
        urlPatterns = {"/registrationPage"}
)
public class RegistrationServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/UserService")
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username;
        String password;
        String email;
        try {
            username = StringEscapeUtils.escapeJava(request.getParameter("username"));
            password = StringEscapeUtils.escapeJava(request.getParameter("password"));
            email = StringEscapeUtils.escapeJava(request.getParameter("email"));
            if (username == null || password == null || email == null || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                throw new Exception("Missing or empty credential value");
            }

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
            return;
        }
        // TODO handle if the user already exists?
        userService.createUser(username, password, email, "user");
        String path = getServletContext().getContextPath() + "/landingPage";
        response.sendRedirect(path);
    }
}
