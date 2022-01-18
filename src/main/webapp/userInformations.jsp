<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<p style="text-align: end; width: 100%">
<c:set var="user" value="${sessionScope.user}"/>
<c:if test="${user != null}">
    You are logged as '${user.getUsername()}'
</c:if>
</p>
