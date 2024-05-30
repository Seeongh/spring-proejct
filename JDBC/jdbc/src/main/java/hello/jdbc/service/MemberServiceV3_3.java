package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Transactional AOP 적용
 */

@Slf4j
public class MemberServiceV3_3 {

    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_3( MemberRepositoryV3 memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 패턴이 있음
     */
    @Transactional //class에 붙일경우 public 적용 메서드가 AOP적용 대상이 됨.
    public void accountTransfer(String fromId, String toId , int money) throws SQLException {

        bizLogic( fromId, toId, money);
    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외발생");
        }
    }


    private void bizLogic (String fromId, String toId, int money) {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update( fromId, fromMember.getMoney() - money);

        //예외발생시
        validation(toMember);
        memberRepository.update( toId, toMember.getMoney() + money);
    }
}
