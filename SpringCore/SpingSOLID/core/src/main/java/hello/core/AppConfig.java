package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //설정, 구성정보다.
public class AppConfig {
//-app 환경구성에대해 역할에 대한 구현을 지정해줌
//역할들도 드러나게 리팩토링    

    @Bean //이 메소드가 spring container에 등록된다.
    public MemberService memberService() { //memberservice역할
        //serviceimpl엔 interface에만 의존하고있음
        //여기서 생성해서 넣어주기 때문에
        // 생성자 주입.
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public static MemoryMemberRepository memberRepository() { //memberRepository 역할
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
    @Bean
    public DiscountPolicy discountPolicy() {
        //할인 정책의 변경으로 Fix -> Rate로 변경됨
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
