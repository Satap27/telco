<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Landing page</title>
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
<body>
<h1 id="page-title">Telco Online Services</h1>
<div class="main-container">
    <div class="access-form-div">
        <h3>Enter your username and password to login</h3>
        <form class="access-form" action="loginPage" method="POST">
            <label for="login-username">Username</label>
            <input id="login-username" type="text" name="username" required> <br>
            <label for="login-password">Password</label>
            <input id="login-password" type="password" name="password" required><br>
            <input type="submit" value="LOGIN">
            <c:if test="${errorMsg != null}">
                <div class="error-msg">${errorMsg}</div>
            </c:if>
            <p>Are you an employee? Login <a href="employeeHomepage">here</a></p>
        </form>
    </div>
    <div class="access-form-div">
        <h3>Register if you don't have an account</h3>
        <form class="access-form" action="registrationPage" method="POST">
            <label for="registration-username">Username</label>
            <input id="registration-username" type="text" name="username" required> <br>
            <label for="registration-password">Password</label>
            <input id="registration-password" type="password" name="password" required><br>
            <label for="registration-email">Email</label>
            <input id="registration-email" type="email" name="email" required><br>
            <input type="submit" value="REGISTER">
            <c:if test="${registrationErrorMsg != null}">
                <div class="error-msg">${registrationErrorMsg}</div>
            </c:if>
        </form>
    </div>
</div>

</body>
</html>
