<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee login</title>
</head>
<body>
<h1>Please log-in</h1>

<form action="employeeLoginPage" method="POST">
    Username: <input type="text" name="username" required> <br>
    Password: <input type="password" name="password" required><br>
    <input type="submit" value="LOGIN">
    <p>${errorMsg}</p>
</form>
</body>
</html>
