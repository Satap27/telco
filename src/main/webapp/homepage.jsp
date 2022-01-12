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
        <%--add whatever other attributes we decide to show--%>
    </div>
    <br>
</c:forEach>
</body>
</html>
