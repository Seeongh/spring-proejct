package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.ex.MyDbException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * SQL Exception translator 추가
 *
 */

@Slf4j
public class MemberRepositoryV4_2 implements MemberRepository{

    private final DataSource dataSource;
    private final SQLExceptionTranslator exTranslator;

    public MemberRepositoryV4_2(DataSource dataSource) {
        this.dataSource = dataSource;
        //datasource정보를 넣어주어야함 그래야 꺼내서 쓸 수 있음
        this.exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "";
        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = pstmt.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        }catch(SQLException e) {
            throw exTranslator.translate("save". sql, e);
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
