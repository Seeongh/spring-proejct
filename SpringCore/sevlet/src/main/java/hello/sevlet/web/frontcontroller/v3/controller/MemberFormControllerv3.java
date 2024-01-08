package hello.sevlet.web.frontcontroller.v3.controller;

import hello.sevlet.web.frontcontroller.ModelView;
import hello.sevlet.web.frontcontroller.v3.Controllerv3;

import java.util.Map;

public class MemberFormControllerv3 implements Controllerv3 {
    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form"); //논리 이름
    }
}
