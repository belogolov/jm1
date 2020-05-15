<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>New user</title>
</head>
<body>
<div>
    <div>
        <div>
            <h2>New user</h2>
        </div>

        <form method="post">
            <label>Name:
                <input type="text" name="name"><br/>
            </label>
            <label>Email:
                <input type="text" name="email"><br/>
            </label>
            <label>Password:
                <input type="text" name="password"><br/>
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
</body>
</html>

