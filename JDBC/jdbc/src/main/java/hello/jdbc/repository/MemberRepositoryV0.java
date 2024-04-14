package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * jdbc - drivermanager사용
 */
@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?,?)";

        /**
         * sql injection : 물음표로 하지않고 문자 더하기로 member Id, money 이런식으로 하게될경우
         * 해당 파라미터를 쿼리문으로 해버리면 로직이 들어오고 db를 조작하게됨
         */
        Connection con = null;
        PreparedStatement pstmt = null; //쿼리를 날림

        try{

            con = getConnect();
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, member.getMemberId()); //파라미터 바인딩
            pstmt.setInt(2, member.getMoney());

            int result =  pstmt.executeUpdate(); //sql문을 실제로 전달함 , 영향 받은 row수 숫자 반환
            return member;
        } catch (SQLException e) {

            log.error("db error", e) ;
            throw e;
        }
        finally {
            //항상 닫아놔야하는데 exception때문에 다못탈까봐 try-catch함
            close(con,pstmt, null);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet resultSet){

        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(stmt != null) {
            try {
                stmt.close();//exception 발생해도 밑에 것 타야함
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if(con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }

    private Connection getConnect() {
        return DBConnectionUtil.getConnection();
    }
}
