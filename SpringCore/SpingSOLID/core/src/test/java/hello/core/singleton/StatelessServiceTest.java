package hello.core.singleton;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class StatelessServiceTest {

    @Test
    @DisplayName("stateless- 지역변수나 파라미터, thread local 이용")
    public void StatelessServiceTest() throws Exception {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatelessService statelessService = ac.getBean("statelessService", StatelessService.class);
        StatelessService statelessService2 = ac.getBean("statelessService", StatelessService.class);

        int price1 = statelessService.order("book", 1000);
        int price2 = statelessService2.order("music", 20000);

    }

    static class TestConfig {
        @Bean
        public StatelessService statelessService() {
            return new StatelessService();
        }

    }
}
