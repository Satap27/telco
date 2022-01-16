package it.polimi.telco.controllers;

import it.polimi.telco.exceptions.InvalidServicePackageException;
import it.polimi.telco.model.Product;
import it.polimi.telco.model.ServiceEntry;
import it.polimi.telco.model.TelcoService;
import it.polimi.telco.model.ValidityPeriod;
import it.polimi.telco.services.ProductService;
import it.polimi.telco.services.ServicePackageService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@WebServlet(
        name = "servicePackageCreation",
        urlPatterns = {"/servicePackageCreation"}
)
public class ServicePackageCreationServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/ServicePackageService")
    private ServicePackageService servicePackageService;
    @EJB(name = "it.polimi.telco.services/ProductService")
    private ProductService productService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name;
        long[] optionalProductsId = null;
        int[] validityPeriodsMonth;
        double[] validityPeriodsFee;
        int fixedPhoneServices = 0;
        int[] mobilePhoneMinutesNumber = new int[0];
        double[] mobilePhoneMinutesFee = new double[0];
        int[] mobilePhoneSMSNumber = new int[0];
        double[] mobilePhoneSMSFee = new double[0];
        int[] fixedInternetGBNumber = new int[0];
        double[] fixedInternetGBFee = new double[0];
        int[] mobileInternetGBNumber = new int[0];
        double[] mobileInternetGBFee = new double[0];
        int servicesNumber;
        try {
            name = StringEscapeUtils.escapeJava(request.getParameter("service-package-name"));
            if (request.getParameterMap().containsKey("optional-products")) {
                optionalProductsId = Arrays.stream(request.getParameterValues("optional-products")).mapToLong(Long::parseLong).toArray();
            }
            validityPeriodsMonth = Arrays.stream(request.getParameterValues("validity-period-months")).mapToInt(Integer::parseInt).toArray();
            validityPeriodsFee = Arrays.stream(request.getParameterValues("validity-period-fee")).mapToDouble(Double::parseDouble).toArray();
            if (request.getParameterMap().containsKey("service-fixed-phone")) {
                fixedPhoneServices = request.getParameterValues("service-fixed-phone").length;
            }
            if (request.getParameterMap().containsKey("service-mobile-phone-minutes-number")) {
                mobilePhoneMinutesNumber = Arrays.stream(request.getParameterValues("service-mobile-phone-minutes-number")).mapToInt(Integer::parseInt).toArray();
                mobilePhoneMinutesFee = Arrays.stream(request.getParameterValues("service-mobile-phone-minutes-fee")).mapToDouble(Double::parseDouble).toArray();
                mobilePhoneSMSNumber = Arrays.stream(request.getParameterValues("service-mobile-phone-sms-number")).mapToInt(Integer::parseInt).toArray();
                mobilePhoneSMSFee = Arrays.stream(request.getParameterValues("service-mobile-phone-sms-fee")).mapToDouble(Double::parseDouble).toArray();
            }
            if (request.getParameterMap().containsKey("service-fixed-internet-number")) {
                fixedInternetGBNumber = Arrays.stream(request.getParameterValues("service-fixed-internet-number")).mapToInt(Integer::parseInt).toArray();
                fixedInternetGBFee = Arrays.stream(request.getParameterValues("service-fixed-internet-fee")).mapToDouble(Double::parseDouble).toArray();
            }
            if (request.getParameterMap().containsKey("service-mobile-internet-number")) {
                mobileInternetGBNumber = Arrays.stream(request.getParameterValues("service-mobile-internet-number")).mapToInt(Integer::parseInt).toArray();
                mobileInternetGBFee = Arrays.stream(request.getParameterValues("service-mobile-internet-fee")).mapToDouble(Double::parseDouble).toArray();
            }
            servicesNumber = fixedPhoneServices + mobilePhoneMinutesNumber.length + fixedInternetGBNumber.length + mobileInternetGBNumber.length;

            if (name == null || name.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty service package name");
            }
            if (validityPeriodsMonth.length == 0 || validityPeriodsFee.length == 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No validity periods provided");
            }
            if (servicesNumber == 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,"No services provided");
            }

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid values");
            return;
        }

        try {
            List<ValidityPeriod> validityPeriods = new ArrayList<>();
            for (int i = 0; i < validityPeriodsMonth.length; i++) {
                validityPeriods.add(new ValidityPeriod(validityPeriodsMonth[i], validityPeriodsFee[i]));
            }

            List<TelcoService> services = new ArrayList<>();
            for (int i = 0; i < fixedPhoneServices; i++) {
                services.add(new TelcoService("fixed phone", new ArrayList<>()));
            }
            for (int i = 0; i < mobilePhoneMinutesNumber.length; i++) {
                List<ServiceEntry> serviceEntries = new ArrayList<>();
                serviceEntries.add(new ServiceEntry("minutes", mobilePhoneMinutesNumber[i], mobilePhoneMinutesFee[i]));
                serviceEntries.add(new ServiceEntry("sms", mobilePhoneSMSNumber[i], mobilePhoneSMSFee[i]));
                services.add(new TelcoService("mobile phone", serviceEntries));
            }
            for (int i = 0; i < fixedInternetGBNumber.length; i++) {
                List<ServiceEntry> serviceEntries = new ArrayList<>();
                serviceEntries.add(new ServiceEntry("gigabytes", fixedInternetGBNumber[i], fixedInternetGBFee[i]));
                services.add(new TelcoService("fixed internet", serviceEntries));
            }
            for (int i = 0; i < mobileInternetGBNumber.length; i++) {
                List<ServiceEntry> serviceEntries = new ArrayList<>();
                serviceEntries.add(new ServiceEntry("gigabytes", mobileInternetGBNumber[i], mobileInternetGBFee[i]));
                services.add(new TelcoService("mobile internet", serviceEntries));
            }

            List<Product> products = productService.findProductsById(optionalProductsId);
            servicePackageService.createServicePackage(name, products, validityPeriods, services);
        } catch (InvalidServicePackageException | NoSuchElementException e) {
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, e.getMessage());
            return;
        }
        String path = getServletContext().getContextPath() + "/employeeHomepage";
        response.sendRedirect(path);
    }
}
