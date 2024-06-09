package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static hello.jdbc.connection.ConnectionConst.*;

/**
 * Transactional - Spring boot 자동 등록
 */
@Slf4j
@SpringBootTest //테스트 돌릴떄 Spring boot Test 연동되어있어서 Spring은 띄워서 빈 등록하고, 주입
public class MemberServiceV3_4Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    @Autowired
    private MemberRepositoryV3 memberRepository;
    @Autowired
    private MemberServiceV3_3 memberService;

    @TestConfiguration //사용하려는 객체들을 빈으로 설정해줌
    static class TestConfig {
//        @Bean
//        DataSource dataSource() {
//            return new DriverManagerDataSource(URL, USERNAME, PASSWORD);
//        }
//
//        @Bean
//        PlatformTransactionManager transactionManager() {
//            return new DataSourceTransactionManager(dataSource()); //스프링 빈에 등록된 빈을 찾아서 써야함
//        }

        private final DataSource dataSource;

        TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }
        @Bean
        MemberRepositoryV3 memberRepositoryV3() {
            return new MemberRepositoryV3(dataSource);
        }

        @Bean
        MemberServiceV3_3 memberServiceV3_3() {
            return new MemberServiceV3_3(memberRepositoryV3());
        }
    }

    @Test
    public void AppCheck(){
        //given
        /**
         * @Transactional이 붙은 class는 BYSpringCGLIB~~ 로 표시됨
         * 서비스 로직을 상속 받아서 템플릿 코드를 만들어 놓고, 트랜잭션 프록시가 로직호출, 트랜잭션 담당.
         */
        log.info("memberService class= {}", memberService.getClass());
        log.info("memberREpository class={}" , memberRepository.getClass());
        //when
        Assertions.assertThat(AopUtils.isAopProxy(memberService)).isTrue();
        //then

     }

    @Test
    @DisplayName("정상 이체")
    public void accountTransfer() throws Exception {
        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);

        //각자 다른 커넥션
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        //같은 커넥션
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);
        //then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberB.getMemberId());


        //exception으로 rollback되어 10000원으로 원복됨 -> 이 테스트는 실패
        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);
        Assertions.assertThat(findMemberB.getMoney()).isEqualTo(12000);

    }

    @Test
    @DisplayName("이체 중 예외 발생")
    public void accountTransferEx() throws Exception {
        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);

        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        //when
        //우리가 던진 예외가 터져야됨.
        Assertions.assertThatThrownBy(() -> memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 20000))
                        .isInstanceOf(IllegalStateException.class);

        memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000);
        //then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberEx.getMemberId());

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);
        Assertions.assertThat(findMemberB.getMoney()).isEqualTo(12000);

    }
}


