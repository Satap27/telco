<%@ page import="it.polimi.telco.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true" %>
<p>
<c:set var="user" value="${sessionScope.user}"/>
    <c:if test="${user != null}">
        ${user.getUsername()}
    </c:if>
</p>
