package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DBConnectionUtil {

    public static Connection getConnection() { //jdbc 표준 i/f가 제공하는 커넥션
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
            log.info("get connection={}, class={}", connection, connection.getClass());
            //jdbc가 제공하는 DriverManager에서 커넥션을 얻어옴
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
