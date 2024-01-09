package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {
        //v1-> 생성하기
//        MemberService memberService = new MemberServiceImpl();
//        OrderService orderService = new OrderServiceImpl();

        //v2-> AppConfig로 IOC, DI
//        AppConfig appConfig = new AppConfig();
//
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();
        
        //v3-> 스프링 사용하기
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        OrderService orderService = ac.getBean("orderService", OrderService.class);

        Long memberId= 1L;
        Member member = new Member(memberId, "a", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itema",10000);
        System.out.println("order= " + order); //객체 출력시 toString에 있는게 출력됨
        System.out.println("order= " + order.calculatePrive()); //객체 출력시 toString에 있는게 출력됨
    }
}
