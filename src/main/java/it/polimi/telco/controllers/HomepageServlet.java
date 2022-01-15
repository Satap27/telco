package it.polimi.telco.controllers;

import it.polimi.telco.model.ServicePackage;
import it.polimi.telco.services.ServicePackageService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "homepage",
        urlPatterns = {"/homepage"}
)
public class HomepageServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/ServicePackageService")
    private ServicePackageService servicePackageService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ServicePackage> servicePackages = null;
        try {
            servicePackages = servicePackageService.getAllServicePackages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("servicePackages", servicePackages);
        request.getRequestDispatcher("/homepage.jsp").forward(request, response);
    }
}
