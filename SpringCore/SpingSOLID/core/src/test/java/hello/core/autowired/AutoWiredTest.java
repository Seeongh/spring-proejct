package hello.core.autowired;

import hello.core.member.Member;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutoWiredTest {
    @Test
    public void AutoWiredOption() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean {
        @Autowired(required=false)//기본은 true, memeber는 spring이 관리하는애가 아니니까 false로설정하면서 호출안함.
        public void setNobean(Member noBean1) {//member는 spring관리 대상이 아님
            System.out.println("noBean1 = " + noBean1);
        }
        @Autowired
        public void setNobean2(@Nullable Member noBean1) {//그래도 호출하고 싶다면 null로 넣는 수도있음.
            System.out.println("noBean2 = " + noBean1);
        }

        @Autowired
        public void setNobean3(Optional<Member> noBean1) {//값이 없으면 empty로 표현해준다.
            System.out.println("noBean3 = " + noBean1);
        }
    }
}
