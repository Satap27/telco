<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Confirmation Page</title>
</head>
<body>
<jsp:include page="userInformations.jsp" />

<h4>Subscription</h4>
<c:set var="user" value="${sessionScope.user}"/>
<c:set var="subscription" value="${sessionScope.subscription}"/>
<c:set var="servicePackage" value="${subscription.getServicePackage()}"/>
<c:set var="validityPeriod" value="${subscription.getValidityPeriod()}"/>
<c:set var="startDate" value="${subscription.getStartDate()}"/>
<c:set var="optionalProducts" value="${subscription.getProducts()}"/>
<c:set var="totalPrice" value="${validityPeriod.getMonthlyFee() * validityPeriod.getNumberOfMonths()}"/>
<div>Service package: ${servicePackage.name}</div>
<div>${validityPeriod.numberOfMonths} months - ${validityPeriod.monthlyFee} €/month</div>
<c:if test="${optionalProducts != null}">
    <c:forEach items="${optionalProducts}" var="optionalProduct">
        <div>${optionalProduct.name} - ${optionalProduct.monthlyFee} €/month</div>
        <c:set var="totalPrice" value="${totalPrice + optionalProduct.getMonthlyFee() * validityPeriod.getNumberOfMonths()}"/>
    </c:forEach>
</c:if>
<div>Starting on: ${startDate}</div>
<div>Total price: ${totalPrice}</div>
<c:if test="${user != null}">
    <form action="confirmation" method="POST">
        <input type="hidden" value="${totalPrice}" name="total-price">
        <input type="submit" value="BUY">
    </form>
</c:if>
<c:if test="${user == null}">
    <a href="${pageContext.request.contextPath}/landingPage">Login / Register</a>
</c:if>
</body>
</html>
