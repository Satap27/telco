package it.polimi.telco.controllers;

import java.io.*;

import it.polimi.telco.exceptions.CredentialsException;
import it.polimi.telco.services.UserService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/UserService")
    private UserService userService;
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String result = null;
        try {
            result = userService.checkCredentials("a", "a").toString();
        } catch (CredentialsException e) {
            e.printStackTrace();
        }
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        out.println(result);
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    public void destroy() {
    }
}

