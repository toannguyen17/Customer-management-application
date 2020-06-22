<%--
  Created by IntelliJ IDEA.
  User: toanv
  Date: 22/06/2020
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Customer</title>
</head>
<body>
    <h1>Create new customer</h1>
    <c:if test='${requestScope["message"] != null}'>
        <p>
        <span class="message">${requestScope["message"]}</span>
        </p>
    </c:if>
    <p>
        <a href="/customers">Back to customer list</a>
    </p>
    <form method="POST">
        <fieldset>
            <legend>Customer information</legend>
            <table>
                <tr>
                    <td>Name: </td>
                    <td><input type="text" name="name" id="name"></td>
                </tr>
                <tr>
                    <td>Email: </td>
                    <td><input type="text" name="email" id="email"></td>
                </tr>
                <tr>
                    <td>Address: </td>
                    <td><input type="text" name="address" id="address"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Create customer"></td>
                </tr>
            </table>
        </fieldset>
    </form>
</body>
</html>
