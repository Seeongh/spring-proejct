package hello.jdbc.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

/**
 * unCheckedException
 * 보통 복구 불가능한 에러로 무시하고 강제로 의존하는 문제를 해결하고,
 * 일관성있게 공통로직으로 빼야함
 * 필요시 catch할 수 있음.
 *
 * 예외를 던질 떄는 구체화된 예외로 던지는게 확인하기 편하지만 그냥 Runtime으로 던져도됨.
 *
 */
@Slf4j
public class unCheckedAppTest {
    @Test
    public void unchecked() { //해결도 못하고 지저분한 코드도 지워짐
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(()->controller.request())
                .isInstanceOf(Exception.class);

     }

    static class Controller {

        Service service = new Service();

        public void request() {
            service.logic();
        }
    }
    static class Service {
      Repository repository = new Repository();
      NetworkClient networkClient = new NetworkClient();

      public void logic() { //check라 던져야함
        repository.call();
        networkClient.call();
      }
    }

    //어떤 네트워크를 통해서 요청하기 위한 로직
    static class NetworkClient {
        public void call() {
            throw new RuntimeConnectException("연결 실패");
        }
    }

    static class Repository {
        public void call() {
            try {
                runSQL();
            } catch (SQLException e) {
                throw new RuntimeSQLException(e);
            }
        }

        public void runSQL () throws SQLException {
            throw new SQLException("ex");
        }
    }

    static class RuntimeConnectException extends RuntimeException {
        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException {

        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }

        public RuntimeSQLException(String message) {
            super(message);
        }
    }
}
