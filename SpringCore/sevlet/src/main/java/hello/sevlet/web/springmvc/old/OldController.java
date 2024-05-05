package hello.sevlet.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 과거에 쓰이던 방식
 * 1. 핸들러 매핑으로 핸들러 조회 - url로 요청이 왔을테니
 * 빈이름으로 핸들러를 찾는 beanNameUrlHandlerMapping 을 실행해서 OldController 반환
 * 2. 해당 핸들러를 핸들할수 있는 어댑터 찾기(SimpleControllerHandlerAdapter)
 * 3. ModelAndView를 통해 new-form 이라는 이름으로 viewResolver호출
 * InternalResourceViewResolver 호출(jsp처럼 포워드를 호출해서 처리할수 있는 경우에 사용)
 * 4. view.render() : forward사용
 */
@Component("/springmvc/old-controller") //이 spring bin의 이름이 저 url이 됨
public class OldController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("oldcontroller.handleRequest");
        return new ModelAndView("new-form");
    }
}
