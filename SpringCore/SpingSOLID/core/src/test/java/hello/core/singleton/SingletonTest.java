package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    public void pureContainer() {

        AppConfig appConfig = new AppConfig();
        //조회 1 : 호출 할 떄 마다 객체를 생성
        MemberService memberService = appConfig.memberService();

        //조회2  : 호출 할 떄 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        //memberService != memberService2
        assertThat(memberService).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    public void singletonServiceTest() throws Exception {
        SingletonService instance = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();
        assertThat(instance).isSameAs(instance2);
        //same : 참조 비교
        //equal : equals 메소드 비교
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    public void SpringContianer() throws Exception {

        //AppConfig appConfig = new AppConfig();

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        //조회 1 : 호출 할 떄 마다 객체를 생성
        //MemberService memberService = appConfig.memberService();
        MemberService memberService = ac.getBean("memberService", MemberService.class);

        //조회2  : 호출 할 떄 마다 객체를 생성
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        //memberService == memberService2
        assertThat(memberService).isSameAs(memberService2);

     }
}
