package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 매니저
 */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV3_1 {
    //private final DataSource dataSource ; 직접사용하는거 문제임
    /**
     * 이걸로 커넥얻어옴 = new DataSourceTransactionManager(); -> JDBC트랜잭션 매니저 구현체
     *  트랜잭션 추상 클래스로, 구현 클래스는 JPA등등 변경할 수 있다.
     */
    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepository;

    public void accountTransfer(String fromId, String toId , int money) throws SQLException {

        //트랜잭션 시작
        //STATUS (상태정보)
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());//먼가 옵션 설정을 해줘야함

        try {

            //비즈니스 로직
            bizLogic( fromId, toId, money);
            transactionManager.commit(status);


        }
        catch(Exception e) {
            transactionManager.rollback(status);
            throw new IllegalStateException(e);
        }
        finally {
            //릴리즈
            //트랜잭션 매니저가 롤백이나 커밋할떄 다 정리해줌
        }

    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외발생");
        }
    }

    private void release(Connection con) {
        if(con != null) {
            try {
                //close 시 connection 이 pool로 돌아가는데 autocommit 이 false로 남아있게됨
                con.setAutoCommit(true);
                con.close();
            }catch (Exception e) {
                log.info("error" ,e);
            }
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
