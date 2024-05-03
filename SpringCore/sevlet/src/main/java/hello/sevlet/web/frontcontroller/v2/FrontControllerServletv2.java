package hello.sevlet.web.frontcontroller.v2;

import hello.sevlet.web.frontcontroller.MyView;
import hello.sevlet.web.frontcontroller.v2.Controllerv2;
import hello.sevlet.web.frontcontroller.v2.controller.MemberFormControllerv2;
import hello.sevlet.web.frontcontroller.v2.controller.MemberListControllerv2;
import hello.sevlet.web.frontcontroller.v2.controller.MemberSaveControllerv2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 반복되는 view호출 부분을 MyView라는 클래스로 빼서,
 * 요청에 대한 응답으로 컨트롤러 처리 후, MyView 생성자에 String만 넣어 반환
 * 반환 받은 MyView에 render를 호출해서
 * 기존 반복되던 화면 호출 코드를 실행
 */
@WebServlet(name="frontControllerServletv2", urlPatterns = "/front-controller/v2/*") //경로 하위에 뭐가있든 여기로옴
public class FrontControllerServletv2 extends HttpServlet {

    private Map<String, Controllerv2> controllerMap = new HashMap<>(); //key는 url value는 컨트롤러

    public FrontControllerServletv2() {

        //매핑정보 저장해두기
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerv2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerv2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerv2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();//이걸로 포트 이후의 경로를 알아올수있음

        //요 컨트롤러v1은 사실 생성된 controller라고 보면된다.이것이 다형성.
        Controllerv2 controller = controllerMap.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        MyView view = controller.process(request,response);
        view.render(request,response);
    }
}

