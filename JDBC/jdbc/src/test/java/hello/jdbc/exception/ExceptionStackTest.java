package hello.jdbc.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

/**
 * 예외포함과 스택 트레이스
 *
 *
 */
@Slf4j
public class ExceptionStackTest {
    @Test
    public void unchecked() { //해결도 못하고 지저분한 코드도 지워짐
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(()->controller.request())
                .isInstanceOf(Exception.class);

     }

     @Test
     public void printEx() throws Exception {
        Controller controller = new Controller();
        try{
            controller.request();
        }
        catch(Exception e) {
            //e.printStackTrace(); //이건 system.out.println라서 실무에서는 log를 사용해야함.
            log.info("ex", e); //로그 출력할때 마지막 파라미터에 예외를 넣어주면 로그에 stack trace를 출력할 수 있다.
        }

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
                throw new RuntimeSQLException(e); //파라미터로 에러가 넘어옴
                /**
                 * 주의!
                 * 실무에서 파라미터로 e를 빠뜨리는 경우가 종종있음.
                 * new RuntimeSQLException();
                 * => 이 경우에는 기존 터진게 출력되지 않음(추적 불가)
                 *
                 * exception만들떄 꼭 Throwable cause로 남겨놔야한다.
                 */
            }
        }

        public void runSQL () throws SQLException {
            throw new SQLException("ex"); //이 터진게 cause by 로 로그에 나옴
            //기존 터졌던거 까지 확인가능
        }
    }

    static class RuntimeConnectException extends RuntimeException {
        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException {

        public RuntimeSQLException(Throwable cause) {
            super(cause); //내부에 기존 exception을 가지고있음
        }

        public RuntimeSQLException(String message) {
            super(message);
        }
    }
}
