<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Homepage</title>
</head>
<body>
<jsp:include page="userInformations.jsp"/>

<h4>Service Packages:</h4>

<c:forEach items="${requestScope.servicePackages}" var="servicePackage">
    <div>
        <div>${servicePackage.name}</div>
        <c:forEach items="${servicePackage.services}" var="service">
            <div>${service.type}</div>
        </c:forEach>
        <c:forEach items="${servicePackage.availableValidityPeriods}" var="validityPeriod">
            <div>${validityPeriod.numberOfMonths}</div>
        </c:forEach>
            <%--add whatever other attributes we decide to show--%>
    </div>
    <br>
</c:forEach>

<a href="${pageContext.request.contextPath}/buyService">Buy service</a>

<c:set var="rejectedOrders" value="${requestScope.rejectedOrders}"/>
<c:if test="${rejectedOrders != null}">
    <br><br><br>
    <div>
        Below you can find the list of rejected orders. By clicking on one of them, you will be redirected to the
        confirmation
        page, in order to retry the payment.
        <form method="post" action="restoreConfirmation">
            <select id="order-id" name="order-id" onchange="this.form.submit();">
                <c:forEach items="${rejectedOrders}" var="rejectedOrder">
                    <option id="">${rejectedOrder.id}</option>
                </c:forEach>
            </select>
        </form>
    </div>
</c:if>
</body>
</html>
