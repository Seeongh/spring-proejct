package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//@Controller //ResponseBody를 계속달아야함 class레벨에 REsponsebody로 붙이면 전체 메소드 리턴이 rESPONSEBODY가됨
@RestController// controller + responsebody
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBodyv1(HttpServletResponse response) throws IOException {

        response.getWriter().write("ok");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responsebodyv2() {
        return  new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responsebodyv3(){
        return "ok"; // @Responsebody로 인해 view로 리턴되지 않고 message로 감(HttpEntity랑 ResponseEntity도 동일)
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responsebodyjsonv1(){
        HelloData helloData = new HelloData();
        helloData.setUsername("pororo");
        helloData.setAge(23);

        return new ResponseEntity<>(helloData, HttpStatus.OK); //hellodata를 전달, http 메세지 컨버터로 json으로 변환됨(동적으로 상태 코드변경가능)
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responsebodyjsonv2(){
        HelloData helloData = new HelloData();
        helloData.setUsername("pororo");
        helloData.setAge(23);

        return helloData; //responseentity사용시 응답코드 쓸 수 있는데 못쓰는경우 @ResponseStatus쓰면됨, 동적으로 쓸 순 없음
    }


}
