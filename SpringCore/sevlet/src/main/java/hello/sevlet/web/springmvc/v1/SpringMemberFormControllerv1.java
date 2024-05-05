package hello.sevlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Controller : 스프링 빈으로 등록, mvc에서 애노테이션 기반 컨트롤러로 인식
 * @RequestMapping : 요청 정보 매핑(url과 메서드 매핑)
 *
 * RequestMappoingHandlerMapping
 */
@Controller
public class SpringMemberFormControllerv1 {
    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process(){
        return new ModelAndView("new-form");//논리 이름
    }
}
