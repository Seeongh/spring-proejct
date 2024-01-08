<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
   <c:forEach var="item" items="${members}">
        <tr>
            <td>아이디 ${item.id}</td>
            <td>이름 ${item.username}</td>
            <td>나이 ${item.age}</td>
        </tr>
   </c:forEach>
</body>
</html>