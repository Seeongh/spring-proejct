package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV2 {
    private final DataSource dataSource ;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId , int money) throws SQLException {

        //커넥션을 얻어야함 여기에 datasource가 필요해짐
        Connection con = dataSource.getConnection();
        try {
            con.setAutoCommit(false); //트랜잭션 수행

            //비즈니스 로직
            bizLogic(con, fromId, toId, money);

            con.commit() ; //정상 수행시 커밋

        }
        catch(Exception e) {
            con.rollback() ; //실패시 롤백
            throw new IllegalStateException(e);
        }
        finally {
            //릴리즈
            release(con);
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

    private void bizLogic(Connection con, String fromId, String toId, int money) {
        Member fromMember = memberRepository.findById(con,fromId);
        Member toMember = memberRepository.findById(con,toId);

        memberRepository.update(con, fromId, fromMember.getMoney() - money);

        //예외발생시
        validation(toMember);
        memberRepository.update(con, toId, toMember.getMoney() + money);
    }
}
