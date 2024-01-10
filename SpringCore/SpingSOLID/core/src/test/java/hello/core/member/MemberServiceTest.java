package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberServiceTest {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    //appconfig의 환경설정정보를 가지고 등록된 bean들을 spring 컨테이너에다가 넣고 관리해줌
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);//뭘 꺼낼거냐면 bean등록한 메서드이름을 꺼내올거야, 반환타입

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
