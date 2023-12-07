package hello.sevlet.web.frontcontroller.v5.adapter;

import hello.sevlet.web.frontcontroller.ModelView;
import hello.sevlet.web.frontcontroller.v4.Controllerv4;
import hello.sevlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controllerv4HandlerAdapter implements MyHandlerAdapter {


    @Override
    public boolean support(Object handler) {
        return (handler instanceof Controllerv4);
    }

    @Override
    public ModelView handler(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        Controllerv4 controller = (Controllerv4) handler;
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        String viewPath = controller.process(paramMap,model);

        ModelView mv = new ModelView(viewPath);
        mv.setModel(model);

        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap =new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName->paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }
}
