package hello.jdbc.exception;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
