package hello.jdbc.exception;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 원칙적으로 런타임에러(언체크)
 * 로직상 의도적으로 반드시 예외를 잡아야하는 경우만 체크예외를 쓴다.
 * 체크예외를 만들어서 처리할수 없을때 예외를 꼭 던지도록 강제한다.
 * EX. 이체실패, 결체 포인트 부족, ID/PW불일치
 *
 * 체크예외가 가지는 문제
 * -컴파일러가 누락을 체크해서 명시적으로 예외를 잡거나 던지도록 선언해야함
 * -하지만 DB연결 실패하거나 NETWORK실패시 서비스 계층에서 처리한다..??
 * -서비스로직에서 처리할 방법이 없음
 * -처리할 수 없어서 밖으로 던진다. 이떄, EXCEPTION, THROWS를 선언해줌
 * -컨트롤러에서도 어려운 처리 throws해서 던진다
 * - was는 서블릿 controlleradvice에서 공통으로 처리하곤하는데 사용자한테 네트워크오류입니다. 라고 띄워주기 애매함.
 * -사용자에는 일반 메세지, 로그로 메세지를 찍음.
 * */
@Slf4j
public class CheckedTest {

    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    public void checked_throw() {
        Service service = new Service();
       // service.callThrow(); //예외를 던졌는데 잡질않음
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf((MyCheckedException.class));
    }
    /**
     * Exception 상속 받은 예외는 체크 예외가 된다.
     */
    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /**
     * Checked  예외는
     * 예외를 잡아서 처리하거나 던지거나 둘중 하나를 필수로 선택해야한다.
     */
    static class Service{
        Repository repository = new Repository();
        /**
         * 예외를 잡아서 처리하는 코드
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                //예외처리 로직
                log.info("예외처리, message ={}", e.getMessage(), e);
            }
            //이후로 정상 동작 -> 성공으로 동작
        }

        /**
         * 체크 예외를 밖으로 던지는 코드
         * 예외를 잡지않고 밖으로 던지려면 throws 예외를 메서드에 필수로 선언
         * @throws MyCheckedException
         */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }

    }

    static class Repository {
        public void call() throws MyCheckedException{ //예외는 잡거나 던져야함
            //checkerror라서 컴파일 에러뜸
            throw new MyCheckedException("ex");
        }
    }
}
