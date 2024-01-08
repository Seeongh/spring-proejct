package hello.sevlet.basic.reqeust;

import ch.qos.logback.core.net.SyslogOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name="requestHeaderServlet", urlPatterns="/request-header")
public class RequestHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintHttpRequest(request);

    }

    public void PrintHttpRequest(HttpServletRequest request) {
        //http요청의 첫라인
        System.out.println("--REQUEST LINE -START -----");
        System.out.println("request.getMethod() = " + request.getMethod());
        System.out.println("request.getProtocol() = " + request.getProtocol());
        System.out.println("request.getScheme() = " + request.getScheme());
        System.out.println("request.getRequestURL() = " + request.getRequestURL());
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        System.out.println("request.getQueryString() = " + request.getQueryString());
        System.out.println("request.isSecure() = " + request.isSecure());
        System.out.println("--REQUEST LINE -END -------");

        //모든 헤더정보
        System.out.println("--Headers -start ---");

        //        Enumeration<String> headerNames= request.getHeaderNames();
        //        while(headerNames.hasMoreElements()) {
        //            String headerName = headerNames.nextElement();
        //            System.out.println(headerName+ " :"+ headerName);
        //        }

        request.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> System.out.println(headerName + ":" + headerName));

        System.out.println("--Headers -end ------");


        //헤더 유틸 조회
        System.out.println("--Header 편의 조회 start---");
        System.out.println("[host 편의 조회");
        System.out.println("request.getServerName() = " + request.getServerName());
        System.out.println("request.getServerPort() = " + request.getServerPort());
        System.out.println("[Accept-Language 편의 조회");
        request.getLocales().asIterator()
                .forEachRemaining(locale -> System.out.println("locale= " + locale));
        System.out.println("request.getLocale() = " + request.getLocale());
        System.out.println("[cookie 편의 조회");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + ":" + cookie.getValue());
            }
        }

        // 기타정보 - remotehost,addr,port/local name,addr,port등...
    }
}