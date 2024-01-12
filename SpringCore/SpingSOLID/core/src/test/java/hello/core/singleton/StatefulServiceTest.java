package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

public class StatefulServiceTest {
    @Test
    @DisplayName("statefulservice test")
    public void statefulServiceSingleton() throws Exception {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);

        //ThreadA: BOOK 1000원주문
        statefulService.order("book", 1000);
        System.out.println("statefulService price: " + statefulService.getPrice());
        //ThreadB: MUSIC 2000원 주문
        statefulService1.order("music", 2000);
        System.out.println("statefulService1 pricce  : " + statefulService1.getPrice());

        System.out.println("statefulService after price: " + statefulService.getPrice());
        Assertions.assertThat(statefulService.getPrice()).isEqualTo(2000);
        //항상 무상태로 설계해야함.
    }

     static class TestConfig {

        @Bean
         public StatefulService statefulService() {
            return new StatefulService();
        }
     }

}