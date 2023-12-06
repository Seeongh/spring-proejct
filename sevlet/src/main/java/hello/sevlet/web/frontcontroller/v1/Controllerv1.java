package hello.sevlet.web.frontcontroller.v1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Controllerv1 {

    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException; //servelt의 service와 같은 모양

}
