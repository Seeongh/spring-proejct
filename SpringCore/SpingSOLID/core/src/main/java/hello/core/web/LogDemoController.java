package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor //Generates a constructor with required argument
public class LogDemoController {

    private final LogDemoService logDemoServices;
    //private final MyLogger myLogger;  //생성해서 의존관계 주입해야하는데 request가 일어나야 생성이되는데 request가없어서 의존관계 주입시 에러
    //private final ObjectProvider<MyLogger> myLoggerProvider; //provider를 사용해서 조회만 해놓음
    private final MyLogger myLogger;


    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        //MyLogger myLogger = myLoggerProvider.getObject();
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");

        Thread.sleep(1000);
        logDemoServices.logic("testId");
        return "ok";
    }

}
