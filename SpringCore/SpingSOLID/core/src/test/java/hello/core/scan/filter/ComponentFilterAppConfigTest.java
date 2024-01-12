package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import static org.springframework.context.annotation.ComponentScan.*;

public class ComponentFilterAppConfigTest {
    @Test
    public void filterScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        beanA beanA = ac.getBean("beanA", beanA.class);
        Assertions.assertThat(beanA).isNotNull();


//        Assertions.assertThrows(
//                NoSuchBeanDefinitionException.class, () -> ac.getBean("beanB", beanB.class));
//        )
    }

    @Configuration
    @ComponentScan(
            includeFilters =  @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class ),
            excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyexcludeComponent.class )
    )
    static class ComponentFilterAppConfig{

    }
}
