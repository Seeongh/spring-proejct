<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="hello.sevlet.domain.member.Member"%>
<%@ page import="hello.sevlet.domain.member.MemberRepository"%>
<%

    MemberRepository  memberRepository =  MemberRepository.getInstance();
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age) ;
    memberRepository.save(member);
%>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id= <%=member.getId()%></li>
    <li>username=<%=member.getUsername()%></li>
    <li>age= <%=member.getAge()%></li>
</ul>
 <a href="/jsp/members/new-form.jsp">메인</a>
</body>
</html>