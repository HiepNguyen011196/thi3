<%--
  Created by IntelliJ IDEA.
  User: hiepn
  Date: 9/7/2022
  Time: 2:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Student Management Application</title>
</head>
<body>
<center>
    <h1>Student Management</h1>
    <h2>
        <a href="/students">List All Student</a>
    </h2>

</center>
<div align="center">
    <form method="post">
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    Edit Student
                </h2>
            </caption>
            <c:if test="${students != null}">
                <input type="hidden" name="id" value="${students.id}"/>
            </c:if>
            <tr>
                <th>Student Name:</th>
                <td>
                    <input type="text" name="name" size="45" value="${students.name}"/>
                </td>
            </tr>
            <tr>
                <th>Student birthDate:</th>
                <td>
                    <input type="text" name="birthDate" size="45" value="${students.birthDate}"
                    />
                </td>
            </tr>
            <tr>
                <th>Student address:</th>
                <td>
                    <input type="text" name="address" size="15" value="${students.address}"
                    />
                </td>
            </tr>
            <tr>
                <th>Student phone:</th>
                <td>
                    <input type="text" name="phone" size="15" value="${students.phone}"
                    />
                </td>
            </tr>
            <tr>
                <th>Student email:</th>
                <td>
                    <input type="text" name="email" size="15" value="${students.email}"
                    />
                </td>
            </tr>
            <tr>
                <th>Student classRoom:</th>
                <td>
                    <input type="text" name="classRoom" size="15" value="${students.classRoom}"
                    />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save"/>
                </td>
            </tr>
        </table>

        <h3>
            <c:if test='${alo != null}'>
                <span style="color: #12faf5"> ${alo} </span>
            </c:if>
        </h3>

    </form>
</div>
</body>
</html>