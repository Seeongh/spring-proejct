package hello.jdbc.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

/**
 * 체크예외의 문제
 * 1. 복구 불가능한 예외 : Spring에서 해결할 수 없음(서블릿 필터, 스프링 인터셉터 등 controllerAdvice를 사용해서 공통로직으로 해결해야함)
 *  ㄴ 개발자가 빨리 인지하는게 중요
 * 2. 의존관계 문제 : 계속 throw를 던짐( 서비스에서 sqlexception을 의존한다.. jdbc라는 기술을 의존하게됨)
 *  ㄴ 상위 클래스인 exception으로 던지면 의존은 해결이 되지만 중요한 예외는 놓치게됨.
 */
@Slf4j
public class CheckedAppTest {
    @Test
    public void checked() {
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(()->controller.request())
                .isInstanceOf(Exception.class);

     }

    static class Controller {

        Service service = new Service();

        public void request() throws SQLException, ConnectException { //계속 던지는중.. 이게 문제임
            service.logic();
        }
    }
    static class Service {
      Repository repository = new Repository();
      NetworkClient networkClient = new NetworkClient();

      public void logic() throws SQLException, ConnectException { //check라 던져야함
        repository.call();
        networkClient.call();
      }
    }

    //어떤 네트워크를 통해서 요청하기 위한 로직
    static class NetworkClient {
        public void call() throws ConnectException {
            throw new ConnectException("연결 실패");
        }
    }

    static class Repository {
        public void call() throws SQLException {
            throw new SQLException("ex"); //checkExcpetion
        }
    }
}
