<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Landing page</title>
</head>
<body>
<h1>Please log-in</h1>

<form action="loginPage" method="POST">
    Username: <input type="text" name="username" required> <br>
    Password: <input type="password" name="password" required><br>
    <input type="submit" value="LOGIN">
    <p>${errorMsg}</p>
</form>

<h1>Please register</h1>
<form action="registrationPage" method="POST">
    Username: <input type="text" name="username" required> <br>
    Password: <input type="password" name="password" required><br>
    Email: <input type="email" name="email" required><br>
    <input type="submit" value="REGISTER">
</form>
</body>
</html>
