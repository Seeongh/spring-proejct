package hello.sevlet.web.servlet;

import hello.sevlet.domain.member.Member;
import hello.sevlet.domain.member.MemberRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name= "memberListServlet", urlPatterns = "/servlet/members")
public class MemberListServlet extends HttpServlet {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {


        List<Member> all = memberRepository.findAll();

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter w = response.getWriter();
        w.write("<html>\n<head>\n +" +
                "<body>성공 \n" );


        for(Member member:all) {
            w.write("<ul><li>id=" + member.getId() +
                    "</li><li>name = " + member.getUsername() +
                    "</li><li>age = " + member.getAge() + "</li></ul>");
        }
        w.write(
                "</head></html>");
    }
}
