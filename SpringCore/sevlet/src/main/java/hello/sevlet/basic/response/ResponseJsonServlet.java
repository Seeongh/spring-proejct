package hello.sevlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.sevlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 응답데이터 - JSON
 * contentType을 application/json으로 설정하고
 * ObjectMapper를 이용해 JSON을 String으로 변화하여 getWriter로 전달
 */
@WebServlet(name="responseJsonServlet",urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private ObjectMapper mapper= new ObjectMapper();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

        //Content-Type: application/json :UTF-8을 기본으로 사용하게 되어있음
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        HelloData helloData=new HelloData();
        helloData.setUsername("ash");
        helloData.setAge(20);

        String result = mapper.writeValueAsString(helloData);
        response.getWriter().write(result);

    }
}
