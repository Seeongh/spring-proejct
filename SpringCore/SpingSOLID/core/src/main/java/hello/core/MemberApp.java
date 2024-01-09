package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class MemberApp {
    public static void main(String[] args) {
        //v1-> 생성하기
//      MemberService memberService = new MemberServiceImpl();

        //v2-> AppConfig로 IOC, DI
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        //v3-> 스프링 사용하기
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        //appconfig의 환경설정정보를 가지고 등록된 bean들을 spring 컨테이너에다가 넣고 관리해줌
        applicationContext.getBean("memberService", MemberService.class)//뭘 꺼낼거냐면 bean등록한 메서드이름을 꺼내올거야, 반환타입

        Member member = new Member(1L, "A", Grade.BASIC);

        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = "+ member.getName());
        System.out.println("find member = " + findMember.getName());
    }
}
