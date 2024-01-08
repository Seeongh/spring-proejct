<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
성공
<ul>
<!--원래 같으면 자바 코드로 request.getAttribute해서 cast해서 getid해야함-->
<!--((member)request.getAttribute("member")).getId()-->
<!-- 밑에는 프로퍼티 접근점 jstl이 제공함-->
    <li>id= ${member.id}</li>
    <li>username=${member.username}</li>
    <li>age= ${member.age}</li>
</ul>
</body>
</html>