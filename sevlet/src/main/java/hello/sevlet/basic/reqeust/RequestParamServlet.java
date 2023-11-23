package hello.sevlet.basic.reqeust;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 1. 파라미터 전송기능
 * http://localhost:8088//request-param?username=hello&age=20
 **/
@WebServlet(name = "RequestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //전체 파라미터 조회
        System.out.println("all param");
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName+ "=" + request.getParameter(paramName))); //이름과 밸류

        //단일 파라미터 조회
        System.out.println("one param");
        String name = request.getParameter("username");
        String age = request.getParameter("age");
        System.out.println("param = " + name + ":" + age);

        //이름이 같은 복수 파라미터, key는 하나고 value가 여러개
        String[] usernames = request.getParameterValues("username");
        for(String names: usernames) {
            System.out.println("username= " + names);
        }

        response.getWriter().write("HI"+name);
    }
}
