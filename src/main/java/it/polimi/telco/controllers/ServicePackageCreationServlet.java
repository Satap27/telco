package it.polimi.telco.controllers;

import it.polimi.telco.services.ServiceEntryService;
import it.polimi.telco.services.ServicePackageService;
import it.polimi.telco.services.TelcoServiceService;
import it.polimi.telco.services.ValidityPeriodService;
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

@WebServlet(
        name = "servicePackageCreation",
        urlPatterns = {"/servicePackageCreation"}
)
public class ServicePackageCreationServlet extends HttpServlet {
    @EJB(name = "it.polimi.telco.services/ServicePackageService")
    private ServicePackageService servicePackageService;
    @EJB(name = "it.polimi.telco.services/ValidityPeriodService")
    private ValidityPeriodService validityPeriodService;
    @EJB(name = "it.polimi.telco.services/TelcoServiceService")
    private TelcoServiceService telcoServiceService;
    @EJB(name = "it.polimi.telco.services/ServiceEntryService")
    private ServiceEntryService serviceEntryService;

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
                throw new Exception("Missing or empty service package name");
            }
            if (validityPeriodsMonth.length == 0 || validityPeriodsFee.length == 0) {
                throw new Exception("No validity periods provided");
            }
            if (servicesNumber == 0) {
                throw new Exception("No services provided");
            }

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }
        try {
            long[] validityPeriodsId = new long[validityPeriodsMonth.length];
            for (int i = 0; i < validityPeriodsMonth.length; i++) {
                validityPeriodsId[i] = validityPeriodService.createValidityPeriod(validityPeriodsMonth[i], validityPeriodsFee[i]);
            }
            List<Long> servicesId = new ArrayList<>();
            for (int i = 0; i < fixedPhoneServices; i++) {
                servicesId.add(telcoServiceService.createTelcoService("fixed phone", null));
            }
            for (int i = 0; i < mobilePhoneMinutesNumber.length; i++) {
                long[] entriesId = new long[2];
                entriesId[0] = serviceEntryService.createServiceEntry("minutes", mobilePhoneMinutesNumber[i], mobilePhoneMinutesFee[i]);
                entriesId[1] = serviceEntryService.createServiceEntry("sms", mobilePhoneSMSNumber[i], mobilePhoneSMSFee[i]);
                servicesId.add(telcoServiceService.createTelcoService("mobile phone", entriesId));
            }
            for (int i = 0; i < fixedInternetGBNumber.length; i++) {
                long entryId = serviceEntryService.createServiceEntry("gigabytes", fixedInternetGBNumber[i], fixedInternetGBFee[i]);
                servicesId.add(telcoServiceService.createTelcoService("fixed internet", new long[]{entryId}));
            }
            for (int i = 0; i < mobileInternetGBNumber.length; i++) {
                long entryId = serviceEntryService.createServiceEntry("gigabytes", mobileInternetGBNumber[i], mobileInternetGBFee[i]);
                servicesId.add(telcoServiceService.createTelcoService("mobile internet", new long[]{entryId}));
            }
            servicePackageService.createServicePackage(name, optionalProductsId, validityPeriodsId, servicesId.stream().mapToLong(i -> i).toArray());
        } catch (Exception e) {
            // TODO rollback?
        }
        String path = getServletContext().getContextPath() + "/employeeHomepage";
        response.sendRedirect(path);
    }
}
