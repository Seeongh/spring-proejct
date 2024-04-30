package hello.sevlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @WebServlet으로 서블릿 애노테이션을 선언
 * name : 서블릿 이름, urlPattern: url매핑
 */
@WebServlet(name="helloServlet", urlPatterns="/hello")
public class HelloServlet extends HttpServlet {
    /**
     * HTTP요청을 통해 매핑된 URL 이 호출되면 서블릿 컨테이너는 service 메서드를 실행
     * spring boot 실행하면서 내장 톰캣이 띄워짐
     * 톰캣은 서블릿컨테이너를 통해 서블릿을 생성함(지금 이 이름이 있는 서블릿)
     * 브라우저가 HTTP 메세지를 생성해서 던지면
     * 서버는 그걸받고 response, request 객체를 만들어서 여기 service에 전달.
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        System.out.println(request.getParameter("username"));
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello");
    }
}
