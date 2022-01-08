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

@WebServlet(
        name = "loginPage",
        description = "JSP Servlet With Annotations",
        urlPatterns = {"/loginPage"}
)
public class LoginServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/UserService")
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
    }

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
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
            return;
        }

        // If the user exists, add info to the session and go to home page, otherwise
        // show login page with error message

        String path;
        if (user == null) {
            request.setAttribute("errorMsg", "Incorrect username or password");
            request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
        } else {
            /*QueryService qService = null;
            try {
                *//*
                 * We need one distinct EJB for each user. Get the Initial Context for the JNDI
                 * lookup for a local EJB. Note that the path may be different in different EJB
                 * environments. In IntelliJ use: ic.lookup(
                 * "java:/openejb/local/ArtifactFileNameWeb/ArtifactNameWeb/QueryServiceLocalBean"
                 * );
                 *//*
                InitialContext ic = new InitialContext();
                // Retrieve the EJB using JNDI lookup
                qService = (QueryService) ic.lookup("java:/openejb/local/QueryServiceLocalBean");
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            request.getSession().setAttribute("user", user);
            // request.getSession().setAttribute("queryService", qService);
            //path = getServletContext().getContextPath() + "/GoToHomePage";
            //response.sendRedirect(path);
            path = getServletContext().getContextPath() + "/homepage";
            response.sendRedirect(path);
        }
    }
}
