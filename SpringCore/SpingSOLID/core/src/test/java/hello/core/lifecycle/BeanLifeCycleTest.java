package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 네트워크, db등 시작에 연결하고 종료시 disconnect 로 연결을 끊어야하는 작업
 * 객체 생성 -> 의존관계 주입의 사이클
 *
 * [빈 새애명주기 콜 백 지원 방법]
 * 1. 인터페이스(InitializingBean, DisposabelBean)
 *  : 스프링 의존적, 메세드 이름 변경 못함, 코드 수정 불가
 * 2. 설정정보 초기화 메서드 , 종료 메서드 지정(initMehod="init", destroyMethod="close")
 *  : 위의 단점 개선
 *  : distroyMehod의 기본값은 inferred추론. -> close, shudown 메서드 자동호출
 * 3. @PostConstruct, @PreDestory 지원
 *  : 편리하고 종속적이지않음
 */
public class BeanLifeCycleTest {
    @Test
    public void lifecycleTest () throws Exception {
        //given
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient bean = ac.getBean(NetworkClient.class);
        ac.close();
        //when

        //then

     }

     @Configuration
    static class LifeCycleConfig{
        //@Bean(initMethod = "init", destroyMethod = "close") //스프링이 콜백 지원하는 법 2. 지정메소드 사용하기

         @Bean
         public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello");
            return networkClient;
        }
     }
}
