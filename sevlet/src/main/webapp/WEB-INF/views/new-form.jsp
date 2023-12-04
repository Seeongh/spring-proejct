<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<!--상대 경로를 사용하여 save로 설정해둠-->
<!--현재 url 이 속한 계층 경로 + /save>-->
<!-- 상대경로로 설정할떄 servlet-mvc/members/new-form에서 호출하여 servlet-mvc/members/save로감-->
    <form action ="save" method="post">
        username: <input type="text" name="username"/>
        age: <input type="text" name="age"/>
        <button type="submit">전송</button>

    </form>
</body>
</html>