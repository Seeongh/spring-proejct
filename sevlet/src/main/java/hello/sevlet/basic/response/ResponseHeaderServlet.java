package hello.sevlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        //[STATUS-LINE]

        redirect(response);
        //response.setStatus(HttpServletResponse.SC_OK); //http 응답 코드 200
        //response.setStatus(HttpServletResponse.SC_BAD_REQUEST); //http 응답 코드 400

        //[RESPONSE-HEADER]
        content(response);
        //  response.setHeader("Content-Type" ,"text/plain;charset=utf8");
        // response.setHeader("Cache-Control","nop-cache, no-store,must-revalidate"); //무효화하게따 쿠키

         cookie(response);
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("my-header","hello");


        response.getWriter().write("앙뇽");
    }


    private void content(HttpServletResponse response) {
        response.setContentType("Text/plain");
        response.setCharacterEncoding("utf-8");
        response.setContentLength(2); //length 지정도 가능하다 ln은 엔터때문에 바이트 길이가 3개가 됨
    }

    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600
        //response.setHeader("Set-Cookie","myCookie=goo; Max-Age=600")
        Cookie cookie = new Cookie("myCookie","good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Lopcation : /basic/hello-form.html

        //response.setStatus(HttpServletResponse.SC_FOUND);
        //response.setHeader("Location","/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html");
    }
}
