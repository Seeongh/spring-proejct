package hello.sevlet.web.frontcontroller.v4;

import hello.sevlet.web.frontcontroller.ModelView;
import hello.sevlet.web.frontcontroller.MyView;
import hello.sevlet.web.frontcontroller.v4.Controllerv4;
import hello.sevlet.web.frontcontroller.v4.controller.MemberFormControllerv4;
import hello.sevlet.web.frontcontroller.v4.controller.MemberListControllerv4;
import hello.sevlet.web.frontcontroller.v4.controller.MemberSaveControllerv4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletv4", urlPatterns = "/front-controller/v4/*") //경로 하위에 뭐가있든 여기로옴
public class FrontControllerServletv4 extends HttpServlet {

    private Map<String, Controllerv4> controllerMap = new HashMap<>(); //key는 url value는 컨트롤러

    public FrontControllerServletv4() {

        //매핑정보 저장해두기
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerv4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerv4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerv4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();//이걸로 포트 이후의 경로를 알아올수있음
        //요 컨트롤러v1은 사실 생성된 controller라고 보면된다.이것이 다형성.
        Controllerv4 controller = controllerMap.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //파라미터를 뽑아서 반복문 돌려서 controller에 먹이는거는 작은 단위라서 따로 뽑음
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        //그 request.getParameter들 map으로 변경해서 컨트롤러에 넘겨줌, 빈 model map
        String viewName = controller.process(paramMap,model);

        MyView view = viewResolver(viewName); //논리이름에 물리이름 입혀서 반환

        //모델을 렌더에 같이 넘겨주어야함
        view.render(model, request,response);
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

