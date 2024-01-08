<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form action ="/jsp/members/save.jsp" method="post">

        username: <input type="text" name="username"/>
        age: <input type="text" name="age"/>
        <button type="submit">전송</button>

    </form>
    <a href="/jsp/members/members.jsp">리스트보기</a>

</body>
</html>