package hello.sevlet.basic.reqeust;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name="requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputstream = request.getInputStream(); //message body를 bytecode로 바로 얻음
        //string으로 변환
        String messagebody = StreamUtils.copyToString(inputstream, StandardCharsets.UTF_8);//spring 제공util, byte->문자는 어떤 인코딩인지 알려줘야함
        System.out.println(messagebody);

        response.getWriter().write("ok");
    }
}
