package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.lang.model.SourceVersion;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 빈 - 상속 관계 조회
 * : 부모 타입으로 조회시 자식 타입도 함께 조회됨
 */

public class ApplicationContextExtendsFindTest {


    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면 중복 오류가 발생한다.")
    public void findBeanByParentTypeDuplicate() throws Exception {
        DiscountPolicy bean = ac.getBean(DiscountPolicy.class);
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));

     }

    @Test
    @DisplayName("부모 타입 조회시 자식이 둘 이상이면 빈이름을 지정하면 된다")
    public void findBeanByParentTypeBeanName() throws Exception {
        DiscountPolicy bean = ac.getBean("rateDiscountPolicy",DiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위타입으로 조회")
    public void findBeanBysubnType() throws Exception {
        DiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모타입으로 하위 다 조회")
    public void findAllBeanByParentType() throws Exception {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(beansOfType.size()).isEqualTo(2);
        for(String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
    }

    @Test
    @DisplayName("부모타입으로 모두 조회하기") //object가 최상
    public void findAllBeanByObjectTYpe() {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for(String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
    }

    @Configuration
    public static class TestConfig {
        @Bean
        public DiscountPolicy rateDiscountPolicy() { //반환값을 DiscountPolicy 로 하는 이유는.. 역할과 구현을 쪼개려고.
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
