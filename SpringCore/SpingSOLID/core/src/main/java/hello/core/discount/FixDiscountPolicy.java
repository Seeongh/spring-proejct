package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.stereotype.Component;

@Component //타입우선으로 찾는 spring의 경우 테스트
public class FixDiscountPolicy implements DiscountPolicy{
    
    private int discountFixAmount = 1000; //1000원 할인
    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){ // enum은 == 쓰는게 맞음
            return discountFixAmount;
        }
        else {
            return 0;            
        }
    }
}
