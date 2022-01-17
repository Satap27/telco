<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sales Report</title>
</head>
<body>
<h4>Purchases per package:</h4>

<c:forEach items="${requestScope.purchasesPerPackage}" var="purchasePerPackage">
    <div>
        <div>${purchasePerPackage.getPackageName()}</div>
        <div>${purchasePerPackage.getPurchases()}</div>
    </div>
    <br>
</c:forEach>

<h4>Purchases per package and period:</h4>

<c:forEach items="${requestScope.purchasesPerPackagePeriod}" var="purchasePerPackagePeriod">
    <div>
        <div>${purchasePerPackagePeriod.getPackageName()}</div>
        <div>${purchasePerPackagePeriod.getMonths()}</div>
        <div>${purchasePerPackagePeriod.getMonthlyFee()}</div>
        <div>${purchasePerPackagePeriod.getPurchases()}</div>
    </div>
    <br>
</c:forEach>

<h4>Revenues per package:</h4>

<c:forEach items="${requestScope.revenuesPerPackage}" var="revenuePerPackage">
    <div>
        <div>${revenuePerPackage.getPackageName()}</div>
        <div>${revenuePerPackage.getRevenuesWithOptions()}</div>
        <div>${revenuePerPackage.getRevenuesWithoutOptions()}</div>
    </div>
    <br>
</c:forEach>

<h4>Average products bought per package:</h4>

<c:forEach items="${requestScope.productsPerPackage}" var="productPerPackage">
    <div>
        <div>${productPerPackage.getPackageName()}</div>
        <div>${productPerPackage.getProductsAverage()}</div>
    </div>
    <br>
</c:forEach>

<h4>Insolvent users:</h4>

<c:forEach items="${requestScope.insolventUsers}" var="insolventUser">
    <div>
        <div>${insolventUser.getUsername()}</div>
    </div>
    <br>
</c:forEach>

<h4>Suspended orders:</h4>

<c:forEach items="${requestScope.suspendedOrders}" var="suspendedOrder">
    <div>
        <div>${suspendedOrder.getUser().getUsername()}</div>
        <div>${suspendedOrder.getTotalPrice()}</div>
    </div>
    <br>
</c:forEach>

<h4>Alerts:</h4>

<c:forEach items="${requestScope.alerts}" var="alert">
    <div>
        <div>${alert.getUser().getUsername()}</div>
        <div>${alert.getAmount()}</div>
        <div>${alert.getRejectionTime()}</div>
    </div>
    <br>
</c:forEach>

<c:set var="bestProduct" value="${requestScope.bestProduct}"/>
<c:if test="${bestProduct != null}">
    <h4>Best product:</h4>
    <div>
        <div>${bestProduct.getProductName()}</div>
        <div>${bestProduct.getValue()}</div>
    </div>
    <br>
</c:if>
</body>
</html>
