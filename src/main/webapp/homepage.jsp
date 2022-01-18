<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Homepage</title>
    <style>
        #page-title {
            width: 100%;
            text-align: center;
            margin-bottom: 100px;
        }

        .service-package-div {
            padding: 20px;
            width: 200px;
            display: flex;
            flex-direction: column;
            align-items: center;
            box-shadow: 0 0 10px #aaa;
            border-radius: 5px;
        }

        .main-container {
            display: flex;
            width: 100%;
            justify-content: center;
            gap: 50px;
            flex-wrap: wrap;
        }

        .access-form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
    </style>
</head>
<body>
<jsp:include page="userInformations.jsp"/>
<h1 id="page-title">Homepage</h1>
<div class="main-container">
    <c:forEach items="${requestScope.servicePackages}" var="servicePackage">
        <div class="service-package-div">
            <div style="margin-bottom: 10px"><b>${servicePackage.getName()}</b></div>
            <c:forEach items="${servicePackage.getServices()}" var="service">
                <div>${service.getType()}</div>
            </c:forEach>
            <div style="margin-top: 10px"><b>Available periods</b></div>
            <c:forEach items="${servicePackage.getAvailableValidityPeriods()}" var="validityPeriod">
                <div>${validityPeriod.getNumberOfMonths()} months - ${validityPeriod.getMonthlyFee()} €/month</div>
            </c:forEach>
        </div>
    </c:forEach>
</div>
<div style="display: flex; width: 100%; justify-content: center;">
    <div style="margin-top: 30px; box-shadow: 0 0 10px #aaa; padding: 20px; width: 200px; text-align: center; border-radius: 5px;">
        <a href="${pageContext.request.contextPath}/buyService">BUY A SERVICE PACKAGE</a>
    </div>
</div>

<c:set var="rejectedOrders" value="${requestScope.rejectedOrders}"/>
<c:if test="${!rejectedOrders.isEmpty()}">
<div class="main-container" style="margin-top: 30px;">
    <div class="service-package-div" style="width: 300px;">
        Below you can find the list of rejected orders. By clicking the button you can retry the payment.
        <form class="access-form" method="post" action="restoreConfirmation" style="margin-top: 10px;">
             <c:forEach items="${rejectedOrders}" var="rejectedOrder">
                    <div>
                        <input id="radio-${rejectedOrder.getId()}" type="radio" name="order-id" value="${rejectedOrder.getId()}"
                               checked>
                        <label for="radio-${rejectedOrder.getId()}">Package '${rejectedOrder.getServicePackage().getName()}' (created on ${rejectedOrder.getCreationDate()})</label>
                    </div>
                </c:forEach>
            <input style="margin-top: 10px;" type="submit" value="RETRY PAYMENT">
        </form>
    </div>
</div>
</c:if>
</body>
</html>
