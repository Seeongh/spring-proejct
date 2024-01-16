package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
