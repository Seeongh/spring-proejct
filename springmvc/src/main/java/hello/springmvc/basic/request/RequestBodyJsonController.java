package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * {"username":"hello", "age":30}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objmapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonv1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputstream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputstream, StandardCharsets.UTF_8);

        log.info("messagebody={}",messageBody );
        HelloData helloData = objmapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonv2(@RequestBody String messagebody) throws IOException {

        log.info("messagebody={}",messagebody );
        HelloData helloData = objmapper.readValue(messagebody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }


    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonv3(@RequestBody HelloData helloData) {

        log.info("messagebody={}",helloData );
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonv4(HttpEntity<HelloData> data) {
        HelloData helloData = data.getBody();
        log.info("messagebody={}",helloData );
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonv5(@RequestBody HelloData data) {
        //json hellodata그대로 들어옴 반환도 data로 반환해봄
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return data; //객체가 html 메세지 컨버터에서 json으로 변경돼서 json이 응답에 박혀서 나감
    }

}
