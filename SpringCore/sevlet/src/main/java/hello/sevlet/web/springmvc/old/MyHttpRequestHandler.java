package hello.sevlet.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 과거에 쓰이던 방식
 * 1. 핸들러 매핑으로 핸들러 조회 - url로 요청이 왔을테니
 * 빈이름으로 핸들러를 찾는 beanNameUrlHandlerMapping 을 실행해서 OldController 반환
 * 2. 해당 핸들러를 핸들할수 있는 어댑터 찾기(HttpRequestHandlerAdapter)
 */
@Component("/springmvc/request-handler")
public class MyHttpRequestHandler implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HttpRequestController");
    }
}
