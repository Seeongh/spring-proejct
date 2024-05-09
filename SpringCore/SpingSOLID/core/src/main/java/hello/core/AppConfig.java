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

    //@Bean -> memberservice -> new MemoryMemberRepository
    //@Bean -> orderservice -> new MemoryMemberRepository, new Fix~~
    //싱글톤이 꺠질거같음 하지만 싱글톤을 보장해줌
    
    //예상
    //1. AppConfig.memberService
    //2. AppConfig.memberRepository
    //3. AppConfig.memberRepository
    //4. AppConfig.orderService
    //5. AppConfig.memberRepository

    //결과 - 싱글톤을 보장해준다.
    //1. AppConfig.memberService
    //2. AppConfig.memberRepository
    //3. AppConfig.orderService
    @Bean //이 메소드가 spring container에 등록된다.
    public MemberService memberService() { //memberservice역할
        //serviceimpl엔 interface에만 의존하고있음
        //여기서 생성해서 넣어주기 때문에
        // 생성자 주입.
        Sysrnttem.out.println("AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemoryMemberRepository memberRepository() {//memberRepository 역할
        System.out.println("AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }
//    public static MemoryMemberRepository memberRepository() { //memberRepository 역할
//        return new MemoryMemberRepository();
//    }
    //static이 포함된경우 singleton보장하지 않음.

    @Bean
    public OrderService orderService() {
        System.out.println("AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
        //return null;
    }
    @Bean
    public DiscountPolicy discountPolicy() {
        //할인 정책의 변경으로 Fix -> Rate로 변경됨
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
