package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingletonWithPrototypeTest1 {
    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean bean = ac.getBean(PrototypeBean.class);
        bean.addCount();

        Assertions.assertThat(bean.getCount()).isEqualTo(1);

        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        bean1.addCount();
        Assertions.assertThat(bean1.getCount()).isEqualTo(1);


    }

    @Test
    void singletoneClinetUsePrototype() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean bean = ac.getBean(ClientBean.class);
        int count1 = bean.logic();

        ClientBean bean1= ac.getBean(ClientBean.class);
        int count2 =  bean1.logic();

        Assertions.assertThat(count1).isEqualTo(2);
    }

    @Scope("singleton")
    static class ClientBean {
        private final PrototypeBean prototypeBean;

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) { //prototype 내놔!라고 요청-> 생성(이시점에 생성되고 반환) -> 주입
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }


    //싱글톤에서 proto 주입받을떄 해당 객체를 계속 생성하도록 하는 법
    @Scope("singleton")
    static class ClientBeanWithProtoType {

        @Autowired
        ApplicationContext ac;

        public int logic() {
            PrototypeBean bean = ac.getBean(PrototypeBean.class);
            bean.addCount();
            int count = bean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count =0 ;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init"+this);//this는 현재 나
        }

        @PreDestroy()
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
