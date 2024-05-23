package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.ex.MyDbException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 예외 누수 문제 해결
 * 체크 예외를 런타임 예외로  변경
 * MemberRepository Interface 사용
 *
 */

@Slf4j
public class MemberRepositoryV4_1 implements MemberRepository{

    @Override
    public Member save(Member member) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = pstmt.getConnection();
        }catch(SQLException e) {
            log.error("db error", e);
            throw new MyDbException(e);
            //throw e;
        }
        finally {

        }

        return member;
    }

    @Override
    public Member findById(String memberId) {
        return null;
    }

    @Override
    public void update(String memberId, int money) {

    }

    @Override
    public void delete(String memberId) {

    }
}
