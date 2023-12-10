package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamv1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("user name = {}, age = {}", username, age);
        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamv2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {
        log.info("user name = {}, age = {}", memberName, memberAge);

        return "ok"; //컨트롤러면서 return 이 string이면 jsp를 찾으니까 restcontroller 쓰던가
        // Responsebody 어노테이션 -> return 값이 응답메세지에 들어감
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamv3(@RequestParam String username,
                                 @RequestParam int age) {
        log.info("user name = {}, age = {}", username, age);

        return "ok"; //컨트롤러면서 return 이 string이면 jsp를 찾으니까 restcontroller 쓰던가
        // Responsebody 어노테이션 -> return 값이 응답메세지에 들어감
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamv4(String username,int age) {
        log.info("user name = {}, age = {}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username, //required = true는 무조건 들어와야함 false는 옵셔널, 자동으로 400
                           @RequestParam(required=false) Integer age) {//false했는데 없으면 500에러, 안나려면 Integer로변경해야함(객체형, null들어가짐, int는 null안들어가서 오류)
        log.info("user name = {}, age = {}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                       @RequestParam(required=false, defaultValue= "-1") int age) {//이러면 requeired도 있으나 마나 해짐
        log.info("user name = {}, age = {}", username, age);

        return "ok";
    }


    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {//모든 요청을 다 받고싶다
        log.info("user name = {}, age = {}", paramMap.get("username"), paramMap.get("age"));

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributev1(@RequestParam String username,
                                   @RequestParam int age) {
        HelloData hello =new HelloData();
        hello.setUsername(username);
        hello.setAge(age);

        log.info("username = {}, age= {}", hello.getUsername(),hello.getAge());
        log.info("hello = {}", hello); //@Data가 toString을 자동으로 만들어줘서 이것도 찍힘 허허

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1.5")
    public String modelAttributev1_5(@ModelAttribute HelloData hello) {

        log.info("username = {}, age= {}", hello.getUsername(),hello.getAge());
        log.info("hello = {}", hello); //@Data가 toString을 자동으로 만들어줘서 이것도 찍힘 허허

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributev2( HelloData hello) {

        log.info("username = {}, age= {}", hello.getUsername(),hello.getAge());
        log.info("hello = {}", hello); //@Data가 toString을 자동으로 만들어줘서 이것도 찍힘 허허

        return "ok";
    }
}
