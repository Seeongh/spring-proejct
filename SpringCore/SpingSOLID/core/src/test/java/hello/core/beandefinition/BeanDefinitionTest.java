package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
