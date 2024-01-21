package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

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

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean4.class, PrototypeBean.class);
        ClientBean4 bean = ac.getBean(ClientBean4.class);
        int count1 = bean.logic();

        ClientBean4 bean1= ac.getBean(ClientBean4.class);
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

    @Scope("singleton")
    static class ClientBean3 {
        
        @Autowired
        private ObjectProvider<PrototypeBean> protoTypeBeanProvider; //ObjectFactory로 변경해도 동작.
        
        public int logic() {
            PrototypeBean protoTypeBean = protoTypeBeanProvider.getObject(); //프로토타입빈을 찾아주는 기능을 제공
            protoTypeBean.getCount();
            protoTypeBean.addCount();
            int count = protoTypeBean.getCount();
            return count;
        }
    }


    //provider get -> 스프링컨테이너를 통해 해당빈을 찾아서 반환.
    //java 표준으로 spring에 의존하지 않음.
    @Scope("singleton")
    static class ClientBean4{

        @Autowired
        private Provider<PrototypeBean> protoTypeBeanProvider; //ObjectFactory로 변경해도 동작.

        public int logic() {
            PrototypeBean protoTypeBean = protoTypeBeanProvider.get(); //프로토타입빈을 찾아주는 기능을 제공
            protoTypeBean.getCount();
            protoTypeBean.addCount();
            int count = protoTypeBean.getCount();
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
