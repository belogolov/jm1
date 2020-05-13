<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title><%= request.getAttribute("description") %></title>
</head>

<body>
<div>
    <h1></h1>
</div>

<div>
    <div>
        <div>
            <h2><%= request.getAttribute("description") %></h2>
        </div>

        <form method="post">
            <label>Name:
                <input type="text" name="name" value="${user.name}"><br />
            </label>
            <label>Email:
                <input type="text" name="email" value="${user.email}"><br />
            </label>
            <label>Password:
                <input type="text" name="password" value="${user.password}"><br />
            </label>
            <h1></h1>
            <table>
                <tr>
                    <td><input type="submit" value="OK" name="OK"/></td>
                    <td><input type="submit" value="Cancel" name="Cancel"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<%--<div>--%>
<%--    <h1>________________________________________________</h1>--%>
<%--</div>--%>

<%--<div>--%>
<%--    <button onclick="location.href='/index'">Back to main</button>--%>
<%--</div>--%>
</body>
</html>

