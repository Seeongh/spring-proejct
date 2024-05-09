package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 객체 지향의 5가지 원칙
 * 1. SRP 단일 책임 원칙
 * 2. DIP 의존관계 원칙
 * 3. OCP 확장에 열리고 변경에 닫히고
 * 4. IOC 제어의 역전(프로그램 흐름은 Appconfig가 가지고있음)
 * 5. DI 의존관계주입(외부에서 구현객체를 주입함)
 *
 * AppConfig = ApplicationContext 스프링컨테이너
 */

public class MemberServiceTest {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    //appconfig의 환경설정정보를 가지고 등록된 bean들을 spring 컨테이너에다가 넣고 관리해줌
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);//뭘 꺼낼거냐면 bean등록한 메서드이름을 꺼내올거야, 반환타입


    /**
     * 구현체까지 모두 의존함
     */
    //MemberService memberService = new MemberServiceImpl();

    @Test
    void join() {
        //given
        Member member = new Member(1L, "A", Grade.BASIC);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);
        //then
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
