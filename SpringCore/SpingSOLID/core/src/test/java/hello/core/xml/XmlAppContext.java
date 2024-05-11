package hello.core.xml;

import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * xml 설정 사용
 * 컴파일 없이 빈 설정 정보 변경
 * GenericXmlApplicationContext 사용
 */
public class XmlAppContext {

    @Test
    public void xmlAppContext() {
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
