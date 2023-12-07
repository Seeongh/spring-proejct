package hello.sevlet.web.frontcontroller.v4;

import java.util.Map;

public interface Controllerv4 {

    /**
     *
     * @param paramMap
     * @param model
     * @return
     */
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
