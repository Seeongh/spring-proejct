package hello.jdbc.exception.translator;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.ex.MyDuplicationKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j

public class ExTranslatorV1Test {

    @RequiredArgsConstructor
    static class Repository {
        private final DataSource dataSource;

        public Member save(Member member) {

            String sql = "insert into member(member_id, money) values(?,?)";
            Connection con = null;
            PreparedStatement pstmt = null;

            try {
                con = dataSource.getConnection();
                 pstmt = con.prepareStatement(sql);
                pstmt.setString(1, member.getMemberId());
                pstmt.setInt(2, member.getMoney());
                return member;
            }catch (SQLException e) {
                //sqlexception 밖으로 안보냄
                if(e.getErrorCode() == 23505) {
                    throw new MyDuplicationKeyException(e);
                }
            }finally {
                JdbcUtils.closeStatement(pstmt);
                JdbcUtils.closeConnection(con);
            }
        }

    }
}
