package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {

    /**
     *
     * @param member
     * @param price
     * @return 할인 대상 금액(결과로 얼마가 할인될건지)
     */
    int discount(Member member, int price);
}
