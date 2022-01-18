<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Confirmation Page</title>
    <style>
        #page-title {
            width: 100%;
            text-align: center;
            margin-bottom: 100px;
        }

        .buy-service-form {
            padding: 20px;
            width: 400px;
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
    </style>
</head>
<body>
<jsp:include page="userInformations.jsp" />

<h1 id="page-title">Subscription summary</h1>
<c:set var="user" value="${sessionScope.user}"/>
<c:set var="subscription" value="${sessionScope.subscription}"/>
<c:set var="totalPrice" value="${sessionScope.totalPrice}"/>
<c:set var="servicePackage" value="${subscription.getServicePackage()}"/>
<c:set var="validityPeriod" value="${subscription.getValidityPeriod()}"/>
<c:set var="startDate" value="${subscription.getStartDate()}"/>
<c:set var="optionalProducts" value="${subscription.getProducts()}"/>

<div class="main-container">
    <div class="buy-service-form">
        <div style="margin-bottom: 5px"><b>Service package</b></div>
        <div style="margin-bottom: 20px">${servicePackage.getName()}</div>
        <div style="margin-bottom: 5px"><b>Validity period</b></div>
        <div style="margin-bottom: 20px">${validityPeriod.getNumberOfMonths()} months - ${validityPeriod.getMonthlyFee()} €/month</div>
        <c:if test="${optionalProducts != null}">
            <div style="margin-bottom: 5px"><b>Optional products</b></div>
            <c:forEach items="${optionalProducts}" var="optionalProduct">
                <div>${optionalProduct.getName()} - ${optionalProduct.getMonthlyFee()} €/month</div>
            </c:forEach>
            <div style="margin-bottom: 20px"></div>
        </c:if>
        <div style="margin-bottom: 5px">Starting on: ${startDate}</div>
        <div style="margin-bottom: 20px">Total price: ${totalPrice}</div>
        <c:if test="${user != null}">
            <form action="confirmation" method="POST">
                <label>
                    FAIL
                    <input type="checkbox" value="1" name="fail">
                </label>
                <input style="margin-right: 10px" type="submit" value="BUY">
            </form>
        </c:if>
        <c:if test="${user == null}">
            <a href="${pageContext.request.contextPath}/landingPage">LOGIN / REGISTER</a>
        </c:if>
    </div>
</div>
</body>
</html>
