package hello.sevlet.web.frontcontroller.v3;

import hello.sevlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface Controllerv3 {

    ModelView process(Map<String, String> paramMap);
}
