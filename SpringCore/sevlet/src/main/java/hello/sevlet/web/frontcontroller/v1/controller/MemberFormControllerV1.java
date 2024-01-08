package hello.sevlet.web.frontcontroller.v1.controller;

import hello.sevlet.web.frontcontroller.v1.Controllerv1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerV1 implements Controllerv1 {

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath); //요경로로 이동할거야! 라고 지정
        dispatcher.forward(request,response); //servlet에서 jsp를 찾아서 호출
    }
}
