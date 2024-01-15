package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    public void findAllBean() throws Exception {
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class,DiscountService.class);
        //AutoAppconfig에서는 componentScan을 통해 모든 component를 끌어 빈를 만듦
        DiscountService discountSer = ac.getBean(DiscountService.class);

        Member userA = new Member(1L, "userA", Grade.VIP);
        //when
        int discountPrice = discountSer.discount(userA,10000,"fixDiscountPolicy");

        //then
        Assertions.assertThat(discountPrice).isEqualTo(1000);

        int ratediscountPrice = discountSer.discount(userA,20000,"fixDiscountPolicy");

        Assertions.assertThat(ratediscountPrice).isEqualTo(2000);
     }

     static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;


        @Autowired
         DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
             this.policyMap = policyMap; //생성자로 모든 bean을 주입받음. 이 맵에도 주입됨 string으로 찾아서 빈참조값을 꺼내줌
             this.policies = policies;

             System.out.println("policyMap = " + policyMap);
             System.out.println("policies = " + policies);
         }

         public int discount(Member member, int price, String discountCode) {
             DiscountPolicy discountPolicy = policyMap.get(discountCode);
             return discountPolicy.discount(member, price);
         }
     }
}
