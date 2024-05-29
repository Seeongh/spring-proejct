package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * 트랜잭션 매니저
 * DataSourceUtils를 사용해 getConnection() //동기화 매니저 접근
 * DataSourceUtils를 사용해 realeaseConnection()
 */
@Slf4j
public class MemberRepositoryV3 {

    private final DataSource dataSource;

    public MemberRepositoryV3(DataSource dataSource) {
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


    private void close(Connection con, Statement stmt, ResultSet resultSet){

        JdbcUtils.closeResultSet(resultSet);
        JdbcUtils.closeStatement(stmt);

        //트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야함
        /**
         * con.close로 닫으면 커넥션이 끝나버림 , 이후로직에서도 transaction 커밋,롤백할때까지도 살아있어야함
         * 트랜잭션을 동기화매니저에서 꺼내썼으면 리포지토리에서는 안닫음 (서비스에서 알아서해라)
         * 트랜잭션 동기화 매니저에서 사용한게 아니면 닫아버림!
         */
        DataSourceUtils.releaseConnection(con, dataSource);
        //        JdbcUtils.closeConnection(con);

    }

    private Connection getConnect() throws SQLException {
        //주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야한다.
        /**
         * 트랜잭션 동기화 매니저가 관리하는 커넥션이 있으면 그 커넥션을 반환
         * 커넥션 없는 경우에는 새로운 커넥션을 생성해서 반환
         */
        Connection con = DataSourceUtils.getConnection(dataSource); //동기화 매니저에서 getResource를 해줌
        log.info("GETcONNECTION = {}, CLASS= {}", con, con.getClass());
        return con;
    }
}
