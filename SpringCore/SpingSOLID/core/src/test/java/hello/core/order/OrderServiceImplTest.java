package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    //순수한 자바로만 텐스트

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DiscountPolicy discountPolicy;

    @Test
    public void createOrder() {
        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository,discountPolicy);
        orderService.createOrder(1L, "itemA", 10000); //수정자에선 null point exception
    }

}