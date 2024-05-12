package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ComponentScan은 Component가 붙은 모든 클래스를 빈으로 등록한다.
 * @Autowired를 통해 자동으로 빈을 찾아 주입한다. (getBean(.class타입)와 동일
 */
public class AutoAppConfigTest {

    @Test
    public void basicScan (){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
