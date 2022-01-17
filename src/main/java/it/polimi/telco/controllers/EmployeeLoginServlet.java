package it.polimi.telco.controllers;

import it.polimi.telco.exceptions.CredentialsException;
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
import java.util.Objects;

@WebServlet(
        name = "employeeLoginPage",
        urlPatterns = {"/employeeLoginPage"}
)
public class EmployeeLoginServlet extends HttpServlet {
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
            request.getRequestDispatcher("/employeeIndex.jsp").forward(request, response);
        } else if (!Objects.equals(user.getRole(), "employee")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You're not an employee");
        } else {
            request.getSession().setAttribute("user", user);
            String path = getServletContext().getContextPath() + "/employeeHomepage";
            response.sendRedirect(path);
        }
    }
}
