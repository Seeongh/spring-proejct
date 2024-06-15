package hello.jdbc.exception.translator;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.ex.MyDbException;
import hello.jdbc.repository.ex.MyDuplicationKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

@Slf4j

public class ExTranslatorV1Test {

    Repository repository;
    Service service;

    @BeforeEach
     void init() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
        repository = new Repository(driverManagerDataSource);
        service = new Service(repository);
    }

    @Test
    public void duplicateKeysave() throws Exception {

        //given
        service.create("myId");
        service.create("myId");//같은 id
        //when

        //then

     }
    @Slf4j
    @RequiredArgsConstructor
    static class Service {
        private final Repository repository;

        public void create(String memberId) {
            //멤버아이디가 중복되면 캐치로 잡음
            try{

                repository.save(new Member(memberId, 0));
                log.info("saveId{}", memberId);
            } catch(MyDuplicationKeyException e) {
                log.info("키 중복, 복구 시도");
                String retryId = generateNewId(memberId);
                log.info(" retryId = {}", retryId) ;
                repository.save(new Member(retryId, 0));
            } catch (MyDbException e) {
                log.info("데이터 접근 계층 예외 ", e);
                throw e;
            }
        }

        private String generateNewId(String memberId) {
            return memberId + new Random().nextInt();
        }
    }
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
                //errorcode는 db마다 다다름
                //spring에서는 스프링이 제공하는 예외를 사용하도록 exception을 추상화함
                if(e.getErrorCode() == 23505) {
                    throw new MyDuplicationKeyException(e);
                }
                throw new MyDbException(e);
            }finally {
                JdbcUtils.closeStatement(pstmt);
                JdbcUtils.closeConnection(con);
            }
        }

    }
}
