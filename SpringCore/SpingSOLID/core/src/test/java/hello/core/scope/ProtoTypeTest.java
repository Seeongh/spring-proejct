package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class ProtoTypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoTypeBean.class);//자동으로 컴포넌트 스캔해서 등록됨

        System.out.println("find prototypebean1");
        ProtoTypeBean bean = ac.getBean(ProtoTypeBean.class);
        System.out.println("find prototypebean2");
        ProtoTypeBean beanw = ac.getBean(ProtoTypeBean.class);

        System.out.println("beanw = " + beanw);
        System.out.println("bean = " + bean);

        Assertions.assertThat(bean).isNotSameAs(beanw);


        ac.close();
    }


    @Scope("prototype")
    static class ProtoTypeBean {

        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("SingletonBean.destroy");
        }
    }
}
