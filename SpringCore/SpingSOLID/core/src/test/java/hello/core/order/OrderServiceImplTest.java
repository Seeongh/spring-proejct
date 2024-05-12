package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 생성자 주입을 선택해야하는 이유
 * 1. 필드 주입의 경우 순수한 자바 테스트 코드에서 동작이 안된다. 
 */
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