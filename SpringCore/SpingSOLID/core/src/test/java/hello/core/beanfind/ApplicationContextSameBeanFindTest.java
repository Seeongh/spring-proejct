package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상이면 중복 오류발생")
    public void findBeanByTypeDuplicate() {
        MemberRepository bean = ac.getBean(MemberRepository.class); //반환 타입이 두개라서 에러발생 NoUniqueBeanDefinitionException
        assertThrows(NoUniqueBeanDefinitionException.class ,
                () ->  ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상이면 빈 이름 지정")
    public void  findBeanByName() {
        MemberRepository bean = ac.getBean("memberRepository1",MemberRepository.class); //반환 타입이 두개라서 에러발생 NoUniqueBeanDefinitionException
        Assertions.assertThat(bean).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    public void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class); //Map 으로 반환
         for(String key : beansOfType.keySet()){
             System.out.println("key = " + key + "value = " + beansOfType.get(key));

         }
        System.out.println(beansOfType);
         assertThat(beansOfType.size()).isEqualTo(2);
    }

    @Configuration
    public static class SameBeanConfig {// class안의 static은 이 scope안에서만 쓰겠다
        //빈이름은 다르고 type은 비슷함

        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberREpository2() {
            return new MemoryMemberRepository();
        }
    }
}
