package hello.core.scope;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SingletonWithPrototypeTest2 {
    @Test
    public void singletonClientUserPrototype() throws Exception {
        final AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Prototype2Bean.class);
        //given
        final ClientBean2 clientBean1 = ac.getBean(ClientBean2.class); //싱글톤 빈 호출
        final int count1 = clientBean1.logic(); //프로토타입 빈 카운트 증가 및 리턴
        assertThat(count1).isEqualTo(1);
        //when

        final ClientBean2 clientBean2 = ac.getBean(ClientBean2.class);
        final int count2 = clientBean2.logic(); //프로토 타입 빈 카운트 증가 및 리턴
        assertThat(count2).isEqualTo(1);

        ac.close();
        //then

     }

    @Scope("singleton") //스프링 끝날때까지 관리
    static class ClientBean2 {
        private final Prototype2Bean prototypeBean;

        //프로토 타입 빈 주입
        public ClientBean2(final Prototype2Bean prototypeBean) { //주입될때마다 다른 객체가 생성됨.
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
      }



    @Scope("prototype") //생성과 주입까지만 관리
    static class Prototype2Bean {
        //given
        private int count =0 ;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            //생성 후 호출
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy() {
            // 종료 직전 호출
            System.out.println("PrototyeBean.destroy");
        }

    }

}
