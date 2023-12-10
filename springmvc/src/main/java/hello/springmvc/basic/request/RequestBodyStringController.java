package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/reqeust-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String s = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);//항상 bytecode 로 문자 받을떄는 인코딩을 지정해줘야함

        log.info("messageBody={}", s);

        response.getWriter().write("ok");

    }

    @PostMapping("/reqeust-body-string-v2")
    public void requestBodyStringv2(InputStream inputStream, Writer responseWriter) throws IOException {

        String s = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);//항상 bytecode 로 문자 받을떄는 인코딩을 지정해줘야함

        log.info("messageBody={}", s);

        responseWriter.write("ok");

    }


    @PostMapping("/reqeust-body-string-v3")
    public HttpEntity<String> requestBodyStringv3(HttpEntity<String> httpEntity) throws IOException {
        //컨버터 두두등장
        //Spring에서 실행해줌 어? 너 string이구나 내가 변환해줄게 HttpMessageConvert
        //String s = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);//항상 bytecode 로 문자 받을떄는 인코딩을 지정해줘야함
        String body = httpEntity.getBody();
        log.info("http body={}", httpEntity);

        return new HttpEntity<>("ok");

    }


    @PostMapping("/reqeust-body-string-v3_5")
    public HttpEntity<String> requestBodyStringv3_5(RequestEntity<String> httpEntity) throws IOException {
        String body = httpEntity.getBody(); //url같은 기능을 더 제공함
        log.info("http body={}", body);

        return new ResponseEntity<>("ok", HttpStatus.CREATED);

    }

    @ResponseBody
    @PostMapping("/reqeust-body-string-v4")
    public String requestBodyStringv4(@RequestBody String body) throws IOException {

        log.info("http body={}", body);

        return "ok";

    }
}
