package hello.jdbc.exception.translator;

import hello.jdbc.connection.ConnectionConst;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class SpringExceptionTranslatorTest {
    DataSource dataSource;

    @BeforeEach
    void init() {
        dataSource = new DriverManagerDataSource(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
    }

    @Test
    public void sqlExceptionErrorCode() {
        //given
        String sql = "select bad grammer"; //잘못된 sql

        //when
        try{
            Connection con = dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {

            Assertions.assertThat(e.getErrorCode()).isEqualTo(42122);
            /**
             * DB마다 코드가 달라서 일일이 처리해주기 어려움.(현실성이 없음)
             * 위에러를 하나하나 다 칠 필요없이 스프링이 제공해주는
             * 예외변환기 제공
             */
           // throw new BadSqlGrammarException(e);
            int errorCode= e.getErrorCode();
            log.info("errorCode= {}", errorCode);
            log.info("error", e);
        }
     }

    /**
     * 스프링 예외 변환기
     */
    @ Test
    void exceptionTranslator() {
        String sql = "select bad grammer";

        try{
            Connection con = dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Assertions.assertThat(e.getErrorCode()).isEqualTo(42122);

            SQLErrorCodeSQLExceptionTranslator exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
            //파라미터로 읽을수 있는 설명, 작성한 sql, 에러를 전달.
            //-> 적절한 스프링 데이터 접근 계층예외로 변환해줌
            //sql-error-codes.xml
            DataAccessException resultEx = exTranslator.translate("select", sql, e);
            log.info("resultEx" , resultEx);
            Assertions.assertThat(resultEx.getClass()).isEqualTo(BadSqlGrammarException.class);
        }


     }
}
