package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 빈 이름 조회
 * getBean(이름), getBean(이름, 구체 타입), getBean(구체타입)
 * 구체타입으로 조회하면 변경 시 유연성 떨어짐
 *
 * BeanFactory : 스프링 빈을 관리하고 조회, getBean 제공
 * ApplicationContext :  BeanFactory 기능 상속 받아 제공
 *  + 메세지 소스국제화, 환경변수, 이벤트, 리소스 조회 등등
 */
public class ApplicationContextBasicFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);


    @Test
    @DisplayName("빈 이름으로 조회")
    public void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        System.out.println("memberService = " + memberService);
        System.out.println("memberService.getClass() = " + memberService.getClass()); //class type

        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    }

    @Test
    @DisplayName("빈 이름없이 타입으로만 조회")
    public void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class); //interface조회하면 구현체가 대상이됨
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    }

    @Test
    @DisplayName("구체타입으로 조회")
    public void findBeanByName2() {
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        System.out.println("memberService = " + memberService);
        System.out.println("memberService.getClass() = " + memberService.getClass()); //class type

        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    }
    
    @Test
    @DisplayName("빈이름으로 조회x")
    public void findBeanByNameX(){
        //ac.getBean("xxxxx", MemberService.class);
        //MemberService xxxx = ac.getBean("xxxxx", MemberService.class);
        assertThrows(NoSuchBeanDefinitionException.class, ()->ac.getBean("xxxxx", MemberService.class)); //이 예외가 터져야 성공
    }

}
