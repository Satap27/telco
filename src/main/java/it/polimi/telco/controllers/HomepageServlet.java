package it.polimi.telco.controllers;

import it.polimi.telco.services.UserService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(
        name = "homepage",
        description = "JSP Servlet With Annotations",
        urlPatterns = {"/homepage"}
)
public class HomepageServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/UserService")
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
