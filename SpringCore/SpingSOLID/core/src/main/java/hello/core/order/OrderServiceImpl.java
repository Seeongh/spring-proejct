package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor //생성자 코드를 그대로 넣어줌 final넣은 필드에!
public class OrderServiceImpl implements OrderService{

    //필드 주입

//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy;


//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //할인정책의 변경으로 다른 정책을 사용해야함
 //   private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    //위으 코드는 DIP, OCP 위반으로 추상화에 의존하도록 코드를 변경한다.

    private final MemberRepository memberRepository; //final - 생성자에서만 값을 셋팅할 수 있다. 컴파일때 잡을 수있다.
    private  final DiscountPolicy discountPolicy;

//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    //생성자 주입
    @Autowired //생성자 하나있어서 생략가능
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        //beantype이 2개여서 Quarilfier로 지정(관련 애노테이션 생성)
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    //일반 메서드 주입- 수정자 주입시랑 비슷한 타이밍에 주입
//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy){
//        this.memberRepository = memberRepository;
//        this.discountPolicy=discountPolicy;
//    }


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
