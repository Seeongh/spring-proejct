package hello.sevlet.web.frontcontroller.v1;

import hello.sevlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.sevlet.web.frontcontroller.v1.controller.MemberListControllerv1;
import hello.sevlet.web.frontcontroller.v1.controller.MemberSaveControllerv1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletv1", urlPatterns = "/front-controller/v1/*") //경로 하위에 뭐가있든 여기로옴
public class FrontControllerServletv1 extends HttpServlet {

    private Map<String, Controllerv1> controllerMap = new HashMap<>(); //key는 url value는 컨트롤러

    public FrontControllerServletv1() {

        //매핑정보 저장해두기
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerv1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerv1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();//이걸로 포트 이후의 경로를 알아올수있음

        //요 컨트롤러v1은 사실 생성된 controller라고 보면된다.이것이 다형성.
        Controllerv1 controller = controllerMap.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controller.process(request,response);
    }
}

