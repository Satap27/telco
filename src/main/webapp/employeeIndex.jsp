<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee login</title>
    <style>
        #page-title {
            width: 100%;
            text-align: center;
            margin-bottom: 100px;
        }

        .access-form-div {
            width: 400px;
            display: flex;
            flex-direction: column;
            align-items: center;
            box-shadow: 0 0 10px #aaa;
            border-radius: 5px;
            height: 350px;
        }

        .access-form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .error-msg {
            margin-top: 10px;
            position: relative;
            padding: 0.75rem 1.25rem;
            margin-bottom: 1rem;
            border: 1px solid #f5c6cb;
            border-radius: 0.25rem;
            color: #721c24;
            background-color: #f8d7da;
        }

        .main-container {
            display: flex;
            width: 100%;
            justify-content: center;
            gap: 50px;
        }
    </style>
</head>
<h1 id="page-title">Telco Online Services</h1>
<div class="main-container">
    <div class="access-form-div">
        <h3>Enter your username and password to login</h3>
        <form class="access-form" action="employeeLoginPage" method="POST">
            <label for="login-username">Username</label>
            <input id="login-username" type="text" name="username" required> <br>
            <label for="login-password">Password</label>
            <input id="login-password" type="password" name="password" required><br>
            <input type="submit" value="LOGIN">
            <c:if test="${errorMsg != null}">
                <div class="error-msg">${errorMsg}</div>
            </c:if>
        </form>
    </div>
</div>
</body>
</html>
