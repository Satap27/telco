package it.polimi.telco.filters;

import it.polimi.telco.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;

/**
 * Servlet Filter implementation class LoggedIn
 */
@WebFilter("/EmployeeFilter")
public class EmployeeFilter implements Filter {

    /**
     * Default constructor.
     */
    public EmployeeFilter() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.print("Employee checker filter executing ...\n");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String employeeLoginPath = req.getServletContext().getContextPath() + "/employeeLandingPage";

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (session.isNew() || user == null || !Objects.equals(user.getRole(), "employee")) {
            res.sendRedirect(employeeLoginPath);
            return;
        }
        // pass the request along the filter chain
        chain.doFilter(request, response);

    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
