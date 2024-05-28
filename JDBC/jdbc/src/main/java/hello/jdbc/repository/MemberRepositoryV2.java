package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC- Conncetion Param : 같은 커넥션을 유지하려고 파라미터에 같은 커넥션을 파람으로 넣어줌
 */
@Slf4j
public class MemberRepositoryV2 {

    private final DataSource dataSource;

    public MemberRepositoryV2(DataSource dataSource) {
        this.dataSource = dataSource;
    }


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

    public Member findById(String memberId) {
        String sql = "select * from member where member_id = ?";

        Connection con= null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Member member = new Member();

        try {
            con = getConnect();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();
            if(rs.next()) {

                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;

            }
            else {
                throw new NoSuchElementException("member not found memberId="+memberId);
            }
        } catch (SQLException e) {
            log.error("db error", e) ;
        }
        finally{
            close(con, pstmt, rs);
        }

        return member;
    }

    public Member findById(Connection con, String memberId) { //같은 connection을 사용해야함
        String sql = "select * from member where member_id = ?";


        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Member member = new Member();

        try {
            //con = getConnect(); //새로운 커넥션으로 여기서는 삭제해줌
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();
            if(rs.next()) {

                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;

            }
            else {
                throw new NoSuchElementException("member not found memberId="+memberId);
            }
        } catch (SQLException e) {
            log.error("db error", e) ;
        }
        finally{
            JdbcUtils.closeResultSet(rs);
            //JdbcUtils.closeConnection(con); //이 커넥션은 닫으면 안됨. SERVICE에서 시작해서 넘겨줄거니까
            JdbcUtils.closeStatement(pstmt);
        }

        return member;
    }

    public void update(String memberId, int money) {
        String sql = "update member set money=? where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnect();
            pstmt = con.prepareStatement(sql) ;
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);
        }catch (SQLException e) {
            log.error("db error", e) ;
        }
        finally{
            close(con, pstmt, null);
        }

    }

    public void update(Connection con, String memberId, int money) {
        String sql = "update member set money=? where member_id = ?";


        PreparedStatement pstmt = null;

        try{
            pstmt = con.prepareStatement(sql) ;
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);
        }catch (SQLException e) {
            log.error("db error", e) ;
        }
        finally{
            //JdbcUtils.closeConnection(con); //이 커넥션은 닫으면 안됨. SERVICE에서 시작해서 넘겨줄거니까
            JdbcUtils.closeStatement(pstmt);
        }

    }
    public void delete(String memberId) {
        String sql = "delete from Member where member_Id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnect();
            pstmt = con.prepareStatement(sql) ;
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            log.error("db error", e) ;
        }
        finally{
            close(con, pstmt, null);
        }

    }

    private void close(Connection con, Statement stmt, ResultSet resultSet){

        JdbcUtils.closeResultSet(resultSet);
        JdbcUtils.closeConnection(con);
        JdbcUtils.closeStatement(stmt);

    }

    private Connection getConnect() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("GETcONNECTION = {}, CLASS= {}", con, con.getClass());
        return con;
    }
}
