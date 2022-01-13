<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Homepage</title>
</head>
<body>
<jsp:include page="userInformations.jsp" />

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
</body>
</html>
