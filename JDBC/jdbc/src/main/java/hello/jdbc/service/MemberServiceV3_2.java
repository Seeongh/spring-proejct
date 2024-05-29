package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 템플릿
 */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV3_2 {

   // private final PlatformTransactionManager transactionManager;
    private final TransactionTemplate txTemplate;
    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
        this.txTemplate = new TransactionTemplate(transactionManager); //트랜잭션 매니저를 주입받고 내부에서는 TransactionTemplate을 씀
        this.memberRepository = memberRepository;
    }

    /**
     * 패턴이 있음
     */
    public void accountTransfer(String fromId, String toId , int money) throws SQLException {

        //트랜잭션 템플릿이 트랜잭션매니저를 감싸고있음
        //템플릿이 패턴을 다 가지고 있음.
        
        txTemplate.executeWithoutResult((status) -> {bizLogic( fromId, toId, money);} );
//        //트랜잭션 시작
//        //STATUS (상태정보)
//        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());//먼가 옵션 설정을 해줘야함
//
//        try {
//
//            //비즈니스 로직
//            bizLogic( fromId, toId, money);
//            transactionManager.commit(status);
//
//
//        }
//        catch(Exception e) {
//            transactionManager.rollback(status);
//            throw new IllegalStateException(e);
//        }
//        finally {
//            //릴리즈
//            //트랜잭션 매니저가 롤백이나 커밋할떄 다 정리해줌
//        }

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
