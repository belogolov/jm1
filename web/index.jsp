<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authorisation</title>
</head>
<body>
<form action="/login" method="POST">
<%--<form method="POST">--%>
    E-mail: <input type="text" name="email"/>
    Password: <input type="password" name="password"/>
    <input type="submit" value="Submit">
</form>
</body>
</html>
