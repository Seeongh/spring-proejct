package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 스프링 빈 설정 메타 정보
 * XML, 자바 등을 읽어 BeanDefinition을 만들면 이 메타 정보를 바탕으로 스프링 컨테이너가 스프링 빈 생성
 *
 * BeanClassName(생성 빈 이름), factoryBeanName(Appconfig) factoryMethodName (빈 생성할 팩토리 메서드)
 * Scope(싱글톤).. 등등
 */
public class BeanDefinitionTest {
    AnnotationConfigApplicationContext ac =  new AnnotationConfigApplicationContext(AppConfig.class) ;

    @Test
    @DisplayName("빈 설정 메타정보확인")
    public void findApplicationVean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for(String beanDefinitionName: beanDefinitionNames){
            BeanDefinition beandefinition =  ac.getBeanDefinition(beanDefinitionName);

            if(beandefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName
                    + "beanDefinition = " + beandefinition);
            }
        }
    }
}
