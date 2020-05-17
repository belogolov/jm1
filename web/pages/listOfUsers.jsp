<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<form method="POST">
    <b>List of users:</b>
    <table>
        <tr>
            <td><input type="submit" value="Add" name="Add"/></td>
            <td><input type="submit" value="Edit" name="Edit"/></td>
            <td><input type="submit" value="Delete" name="Delete"/></td>
        </tr>
    </table>
    <br/>
    <table style=" width: 100%; border: 4px double black;">
        <tr>
            <th style="border: 1px solid black; text-align: center"></th>
            <th style="border: 1px solid black; text-align: center">Name</th>
            <th style="border: 1px solid black; text-align: center">Email</th>
            <th style="border: 1px solid black; text-align: center">Password</th>
        </tr>
        <c:forEach var="user" items="${listUsers}">
            <tr>
                <td style="border: 1px solid black; text-align: center"><input type="radio" name="id"
                                                                               value="${user.id}"></td>
                <td style="border: 1px solid black; text-align: left"><c:out value="${user.name}"/></td>
                <td style="border: 1px solid black; text-align: left"><c:out value="${user.email}"/></td>
                <td style="border: 1px solid black; text-align: left"><c:out value="${user.password}"/></td>
            </tr>
        </c:forEach>
    </table>
</form>
</body>
</html>
