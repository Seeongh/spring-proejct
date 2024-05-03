package hello.sevlet.web.servlet;

import hello.sevlet.domain.member.MemberRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 처음 서블릿을 통해 html 을 제공하였으나
 * 코드가 많아지면서 지져분해지고 힘들어짐
 *
 * 변경의 라이프 사이클
 * ui변경과 로직 변경은 서로 수정시 관련없는요소일 확률이 높음
 * 이런경우 유지 보수를 위해 나눠주는게 편함
 */
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
