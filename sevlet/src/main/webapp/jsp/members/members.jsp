<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="hello.sevlet.domain.member.Member"%>
<%@ page import="hello.sevlet.domain.member.MemberRepository"%>
<%@ page import="java.util.List"%>
<%
    MemberRepository memberRepository = MemberRepository.getInstance();
    List<Member> members = memberRepository.findAll();
%>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<%
   for(Member member:members) {
               out.write("<ul><li>id=" + member.getId() +
                       "</li><li>name = " + member.getUsername() +
                       "</li><li>age = " + member.getAge() + "</li></ul>");
           }
%>
</body>
</html>