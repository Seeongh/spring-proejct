package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 정책 변경 
 * OrderServiceImpl 코드가 DiscountPolicy 구현체에 의존함
 */
class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("vip는 10%할인이 적용되어야한다.")
    public void vip_o() throws Exception {
        //given
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        //when
        int discount = discountPolicy.discount(member, 10000);
        //then
        assertThat(discount).isEqualTo(1000);

     }


     @Test
     @DisplayName("vip가 아니면 할인이 적용되지 않아야한다.")
     public void vip_x() throws Exception {
         //given
         Member member = new Member(1L, "member", Grade.BASIC);
         //when
         int discount = discountPolicy.discount(member, 1000);
         //then
         assertThat(discount).isEqualTo(1000); //할인안되서 0 이 오는게 맞음
      }
}