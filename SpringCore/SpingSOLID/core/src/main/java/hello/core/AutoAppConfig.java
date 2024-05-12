package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * 컴포넌트 스캔
 * Bean 으로 등록한 클래스 없음
 * Member, Order, Discount 등 @Component 애노테이션을 붙여 스캔의 대상이 됨
 *
 * 컴포넌트 스캔을 통해
 * controller : mvc컨트롤러 인식 service: 개발자들이 비즈니스 계층 인식 도움
 * Repository : 스프링 데이터 접근 계층으로 인식하고, 예외를 스프링 예외로 변환
 * Configuration : 설정 정보로 인식, 싱글톤 유지
 */
@Configuration
@ComponentScan( //스프링빈을 끌어서 자동으로 끌어올림 , @component 붙은애들을 다 bean으로 등록해줌
        basePackages = "hello.core", //어디부터 스캔할지 지정, 하위는 다 탐색함. 시작 class도 지정가능,
        // default는 @componentScan이 붙은 package(hello.core)부터 시작
        // 권장은 패키지 위치를 지정하지않고 설정정보 클래스 위치를 최상단에 둠(basepackages생략가능)
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = Configuration.class)
         //자동등록에서 제외할거 (  이전의 Config 파일)
        //밑에는 @bean 클래스가 하나도 없음
)
public class AutoAppConfig {

//    @Bean(name = "memoryMemberRepository") //수동vs자동 충돌 - 오버라이딩 빈 데피니션(수동등록빈이 우선권)
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }

}
