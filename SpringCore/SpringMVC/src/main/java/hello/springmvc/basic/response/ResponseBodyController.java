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

/**
 * 응답 3가지 방식 1. 정적리소스 2.뷰템플리 3.HTTP 메세지
 *
 * http 메시지 컨버터
 * JSON 데이터를 HTTP 메세지 바디에서 직접 읽고 씀
 * 이경우, viewResolver가 아닌 HttpMessageConverter동작
 * 이때 컨트롤러 반환 타입과 Accept헤더를 조합해서 선택됨
 *
 * [읽기]
 * 1. canRead()로 읽을 수 있는지 확인 2. 대상 클래스 지원여부 확인, Content-Type 미디어 타입 지원 확인 3. read()호출해서 객체 생성, 반환
 * [응답]
 * 1. canWrite()로 쓸 수 있는지 확인 2. 대상 클래스 지원여부 확인,  Accept미디어 타입 지원 확인 3. write()호출해서 데이터 생성
 *
 * 이 HttpMessageConverter는 RequestMappingHnadlerAdapter에 있음
 * 요청은 ArgumentResolver로 파라미터 처리 (이때 컨버터 참조)
 * 응답은 ReturnValueHandler로 응답값 변환 처리 (이때 컨버터 참조)
 */
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
