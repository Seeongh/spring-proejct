package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //할인정책의 변경으로 다른 정책을 사용해야함
 //   private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    //위으 코드는 DIP, OCP 위반으로 추상화에 의존하도록 코드를 변경한다.
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId); //멤버를 찾음
        int discountPrice = discountPolicy.discount(member, itemPrice); //단일 책임의 원칙-order가 할인은 몰라도됨
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //test용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
