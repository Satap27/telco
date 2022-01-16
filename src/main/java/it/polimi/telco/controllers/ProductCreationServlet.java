package it.polimi.telco.controllers;

import it.polimi.telco.services.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

@WebServlet(
        name = "productCreation",
        urlPatterns = {"/productCreation"}
)
public class ProductCreationServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/ProductService")
    private ProductService productService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name;
        double monthlyFee;
        try {
            name = StringEscapeUtils.escapeJava(request.getParameter("product-name"));
            monthlyFee = Double.parseDouble(request.getParameter("product-monthly-fee"));
            if (name == null || name.isEmpty() || monthlyFee < 0) {
                throw new Exception("Missing or empty product name");
            }

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty product name");
            return;
        }
        productService.createProduct(name, monthlyFee);
        String path = getServletContext().getContextPath() + "/employeeHomepage";
        response.sendRedirect(path);
    }
}
