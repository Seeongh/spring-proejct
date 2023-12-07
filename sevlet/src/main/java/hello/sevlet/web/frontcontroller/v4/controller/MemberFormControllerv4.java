package hello.sevlet.web.frontcontroller.v4.controller;

import hello.sevlet.web.frontcontroller.v4.Controllerv4;

import java.util.Map;

public class MemberFormControllerv4 implements Controllerv4 {
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        return "new-form"; //model view return 할 필요없음
    }
}
