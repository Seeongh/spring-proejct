package hello.sevlet.basic.reqeust;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.sevlet.basic.HelloData;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name="requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    //spring은 기본으로 잭슨을 씀 for json
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletInputStream inputStream = req.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println(messageBody); //{"username":"hello"."age":"20"} content-type: json

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class); //변환이 쫘악 된다.

        System.out.println("helloData.username= " + helloData.getUsername() );
        System.out.println("helloData.age= " + helloData.getAge() );

    }
}
