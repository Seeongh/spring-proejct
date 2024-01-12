package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    public void configrationTest() throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository2 = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository = memberService.getMemberRepository();
        MemberRepository memberRepository1 = orderService.getMemberRepository();

        //then
        //지금 다른데 이건 static이 포함되어서.
        assertThat(memberRepository).isSameAs(memberRepository1);
        assertThat(memberRepository2).isSameAs(memberRepository1);
     }

     @Test
    public void configurationDeep() {
         AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
         AppConfig bean = ac.getBean(AppConfig.class);

         System.out.println("bean =" + bean.getClass()); //class hello.core.AppConfig$$EnhancerBySpringCGLIB$$4939b15
         //spring이 CGLIB 라이브로 Appconfig 클래스를 상속받은 다른 클래스를 만들어서 등록한거임
         //이 임의의 클래스가 싱글톤을 보장해준다.
     }
}
