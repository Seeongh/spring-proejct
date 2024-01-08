package hello.sevlet.web.frontcontroller.v2;

import hello.sevlet.web.frontcontroller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Controllerv2 {

    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException; //servelt의 service와 같은 모양
}
