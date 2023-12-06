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
<!-- 이게 상대 경로라서 frontcontroller로 url변경 후에도 그대로 재사용이 가능했음 마지막것만 사악 지우고 save로가기때문에-->
    <form action ="save" method="post">
        username: <input type="text" name="username"/>
        age: <input type="text" name="age"/>
        <button type="submit">전송</button>

    </form>
    <a href ="/servlet-mvc/members">목록</a>
</body>
</html>