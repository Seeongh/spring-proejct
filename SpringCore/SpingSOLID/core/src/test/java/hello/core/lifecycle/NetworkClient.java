package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//public class NetworkClient implements InitializingBean, DisposableBean { //스프링이 콜백 지원하는 법 1. interface이용하기
public class NetworkClient {
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url  = " + url);
        //객체 생성시 불러보기
//        connect();
//        call("초기화메세지");
    }

    public void setUrl(String url ) {
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message" + message);
    }

    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("close : "+ url);
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        //의존관계 주입 후에 지원하겠다.
//        System.out.println("NetworkClient.afterPropertiesSet");
//        connect();
//        call("초기화메세지");
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        //소멸전에 콜백 지원하겠다.
//        //컨테이너 종료 전에 빈들이 죽는데 그떄 닫아주는 처리같은걸 할 수 있음.
//        System.out.println("NetworkClient.destroy");
//        disconnect();
//    }


    @PostConstruct //스프링이 콜백 지원하는 법  3. Annotation 이용하기
    public void init() throws Exception {
        //의존관계 주입 후에 지원하겠다.
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화메세지");
    }

    @PreDestroy
    public void close() throws Exception {
        //소멸전에 콜백 지원하겠다.
        //컨테이너 종료 전에 빈들이 죽는데 그떄 닫아주는 처리같은걸 할 수 있음.
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
}
