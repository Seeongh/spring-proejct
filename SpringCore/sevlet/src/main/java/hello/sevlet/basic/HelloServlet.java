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
 *
 * [서블릿내부 동작]
 * 와스(톰캣) 안에 서블릿 컨테이너가 자동으로 서블릿 객체를 생성하고 , 호출해줌(와스종료시 서블릿도 종료해줌 -생명주기 관리)
 * url 로 요청이 들어오면 요청메세지 기반으로Request,Response객체 만들어서 서블릿 객체 호출
 *  (- request, response는 매번 새로 만드는게 맞지만 , servlet은 항상 새로 생성할필요가없어 싱글톤으로 재사용됨)
 *  (- was는 동시 요청을 위한 멀티 쓰레드 처리 지원)
 * hello 서비스 실행
 * response 객체를 보고 응답
 */
@WebServlet(name="helloServlet", urlPatterns="/hello")
public class HelloServlet extends HttpServlet {
    /**
     * HTTP요청을 통해 매핑된 URL 이 호출되면 서블릿 컨테이너는 service 메서드를 실행
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
