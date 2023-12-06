package hello.sevlet.web.frontcontroller.v2.controller;

import hello.sevlet.domain.member.Member;
import hello.sevlet.domain.member.MemberRepository;
import hello.sevlet.web.frontcontroller.MyView;
import hello.sevlet.web.frontcontroller.v2.Controllerv2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberSaveControllerv2 implements Controllerv2 {
    MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //요청정보 받기
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        //비즈니스 로직 호출
        Member member = new Member(username,age);
        memberRepository.save(member);

        //Model에 데이터 보관
        request.setAttribute("member",member);

        String viewPath = "/WEB-INF/views/save-result.jsp";

        return new MyView(viewPath);
    }
}
