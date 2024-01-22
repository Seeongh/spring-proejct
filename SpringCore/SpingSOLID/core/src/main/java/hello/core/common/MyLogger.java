package hello.core.common;

import org.apache.logging.log4j.message.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;


@Component
@Scope(value ="request", proxyMode = ScopedProxyMode.TARGET_CLASS
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message){
        System.out.println("["+uuid+"]"+ "["+requestURL+"]"+ message);
    }

    @PostConstruct//생성이후
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("["+uuid+"] request scope bean create : "+this);
    }

    @PreDestroy
    public void close(){
        System.out.println("["+uuid+"] request scope bean destroy : "+this);
    }
}
