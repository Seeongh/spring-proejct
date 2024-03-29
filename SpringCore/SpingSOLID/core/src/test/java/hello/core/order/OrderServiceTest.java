package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderServiceTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    OrderService orderService = ac.getBean("orderService", OrderService.class);
   // MemberService memberService = new MemberServiceImpl();
   // OrderService orderService = new OrderServiceImpl();

    @Test
    void createOrder() {
        //given
        Member member = new Member(1L,"A", Grade.VIP);
        memberService.join(member);

        Long memberId = 1L;
        String itemName = "itemA";
        int itemPrice = 10000;

        //when
        Order order = orderService.createOrder(memberId, itemName, itemPrice);

        //then
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}
