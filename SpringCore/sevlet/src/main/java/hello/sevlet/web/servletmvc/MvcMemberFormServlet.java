package hello.sevlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 변경 라이프 사이클 분리로 인해
 * 해당 servlet에서는 요청한 경로로 전달.
 *
 * forward : 서버 내부에서 재호출
 * redirect : client로 요청이 나갔다가 다시 호출
 */
@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {
    //mvc에서 요청은 항상 controller로 들어와야함
    //떄문에 밑은 jsp 만돌려줌
    //사용자가 위의 url로 호출을하면 밑의 service 메소드에서 해당 jsp를 서버내부에서 호출함, 즉 제어권을 넘김
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath); //요경로로 이동할거야! 라고 지정
        //서버 내부에서 다시 호출하여 해당 화면으로 감
        dispatcher.forward(req,resp); //servlet에서 jsp를 찾아서 호출
    }
}
