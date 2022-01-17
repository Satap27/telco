package it.polimi.telco.controllers;

import it.polimi.telco.model.Product;
import it.polimi.telco.services.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "employeeHomepage",
        urlPatterns = {"/employeeHomepage"}
)
public class EmployeeHomepageServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/ProductsService")
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> optionalProducts = null;
        try {
            optionalProducts = productService.getAllProducts();
        } catch (Exception e) {
            // TODO throw
            e.printStackTrace();
        }
        request.setAttribute("optionalProducts", optionalProducts);
        request.getRequestDispatcher("/employeeHomepage.jsp").forward(request, response);
    }
}
