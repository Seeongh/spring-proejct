package hello.sevlet.web.frontcontroller.v2.controller;

import hello.sevlet.web.frontcontroller.MyView;
import hello.sevlet.web.frontcontroller.v2.Controllerv2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerv2 implements Controllerv2 {

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        return new MyView("/WEB-INF/views/new-form.jsp");
    }
}
