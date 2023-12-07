package hello.sevlet.web.frontcontroller.v5;

import hello.sevlet.web.frontcontroller.ModelView;
import hello.sevlet.web.frontcontroller.MyView;
import hello.sevlet.web.frontcontroller.v3.Controllerv3;
import hello.sevlet.web.frontcontroller.v3.controller.MemberFormControllerv3;
import hello.sevlet.web.frontcontroller.v3.controller.MemberListControllerv3;
import hello.sevlet.web.frontcontroller.v3.controller.MemberSaveControllerv3;
import hello.sevlet.web.frontcontroller.v4.Controllerv4;
import hello.sevlet.web.frontcontroller.v4.controller.MemberFormControllerv4;
import hello.sevlet.web.frontcontroller.v4.controller.MemberListControllerv4;
import hello.sevlet.web.frontcontroller.v4.controller.MemberSaveControllerv4;
import hello.sevlet.web.frontcontroller.v5.adapter.Controllerv3HandlerAdapter;
import hello.sevlet.web.frontcontroller.v5.adapter.Controllerv4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletv5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletv5 extends HttpServlet {

    private final Map<String, Object> handlerMappingmap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletv5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new Controllerv3HandlerAdapter());
        handlerAdapters.add(new Controllerv4HandlerAdapter());
    }

    private void initHandlerMappingMap() {

        handlerMappingmap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerv3());
        handlerMappingmap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerv3());
        handlerMappingmap.put("/front-controller/v5/v3/members", new MemberListControllerv3());

        //v4추가
        handlerMappingmap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerv4());
        handlerMappingmap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerv4());
        handlerMappingmap.put("/front-controller/v5/v4/members", new MemberListControllerv4());

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Object handler = getHandler(request);
        if(handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView mv = adapter.handler(request,response,handler);

        String viewName = mv.getViewName(); //컨트롤러가 리턴한 논리이름 받아오기
        MyView view = viewResolver(viewName); //논리이름에 물리이름 입혀서 반환

        //모델을 렌더에 같이 넘겨주어야함
        view.render(mv.getModel(), request,response);
    }

    private MyHandlerAdapter getHandlerAdapter (Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if(adapter.support(handler)) {//이 핸들러를 지원하면
                return adapter;

            }
        }
        throw new IllegalArgumentException("handler adapter not found"+handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();//이걸로 포트 이후의 경로를 알아올수있음
        //요 컨트롤러v1은 사실 생성된 controller라고 보면된다.이것이 다형성.
        return handlerMappingmap.get(requestURI);
    }
    private MyView viewResolver(String viewName) {
        return  new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

}
