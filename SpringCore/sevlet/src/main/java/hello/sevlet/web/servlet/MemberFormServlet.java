package hello.sevlet.web.servlet;

import hello.sevlet.domain.member.MemberRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = " memberFormServlet" , urlPatterns =  "/servlet/members/new-form")
public class MemberFormServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");

        PrintWriter w = response.getWriter();
        w.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form action =\"/servlet/member/save\" method=\"post\">\n" +
                "\n" +
                "    username: <input type=\"text\" name=\"username\"/>\n" +
                "    age: <input type=\"text\" name=\"age\"/>\n" +
                "    <button type=\"submit\">전송</button>\n" +
                "\n" +
                "</form>\n" +
                "\n" +
                "</body>\n" +
                "</html>");
    }
}
