<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<p>
<c:set var="user" value="${sessionScope.user}"/>
<c:if test="${user != null}">
    ${user.getUsername()}
</c:if>
</p>
