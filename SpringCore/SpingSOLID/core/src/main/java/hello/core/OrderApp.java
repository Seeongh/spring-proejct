package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        
        Long memberId= 1L;
        Member member = new Member(memberId, "a", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itema",10000);
        System.out.println("order= " + order); //객체 출력시 toString에 있는게 출력됨
        System.out.println("order= " + order.calculatePrive()); //객체 출력시 toString에 있는게 출력됨
    }
}
