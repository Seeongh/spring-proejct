package hello.sevlet.web.frontcontroller.v3;

import hello.sevlet.web.frontcontroller.ModelView;
import hello.sevlet.web.frontcontroller.MyView;
import hello.sevlet.web.frontcontroller.v3.controller.MemberFormControllerv3;
import hello.sevlet.web.frontcontroller.v3.controller.MemberListControllerv3;
import hello.sevlet.web.frontcontroller.v3.controller.MemberSaveControllerv3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * request 의 parameter를 map에 넣고, 해당 map을 controller에 던짐
 * modelView객체 등장
 * modelView 생성자로 view 이름을 받아 셋팅하고,
 * 해당 객체의 model(map)에  결과 값을 넣어 반환
 * 반환한 view이름 + 공용 경로 더해주고,
 *  model와 view이름 렌더
 *
 *  model은 request.setAttribute 로 반환
 */
@WebServlet(name="frontControllerServletv3", urlPatterns = "/front-controller/v3/*") //경로 하위에 뭐가있든 여기로옴
public class FrontControllerServletv3 extends HttpServlet {

    private Map<String, Controllerv3> controllerMap = new HashMap<>(); //key는 url value는 컨트롤러

    public FrontControllerServletv3() {

        //매핑정보 저장해두기
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerv3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerv3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerv3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();//이걸로 포트 이후의 경로를 알아올수있음

        //요 컨트롤러v1은 사실 생성된 controller라고 보면된다.이것이 다형성.
        Controllerv3 controller = controllerMap.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //파라미터를 뽑아서 반복문 돌려서 controller에 먹이는거는 작은 단위라서 따로 뽑음
        Map<String, String> paramMap = createParamMap(request);

        //그 request.getParameter들 map으로 변경해서 컨트롤러에 넘겨줌
        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName(); //컨트롤러가 리턴한 논리이름 받아오기
        MyView view = viewResolver(viewName); //논리이름에 물리이름 입혀서 반환

        //모델을 렌더에 같이 넘겨주어야함
        view.render(mv.getModel(), request,response);
    }

    private MyView viewResolver(String viewName) {
       return  new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap =new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName->paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }
}

