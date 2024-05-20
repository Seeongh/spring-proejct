package hello.jdbc.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UncheckedTest {

    @Test
    public void unchecked_catch() throws Exception {
        //given
        Service service = new Service() ;
        service.callCatch();
        //when

        //then

     }

     @Test
     public void unchecked_throws() throws Exception {
         //given
         Service service = new Service();
        // service.callThrow();

         Assertions.assertThatThrownBy(() -> service.callThrow())
                 .isInstanceOf((MyUncheckedExcption.class));

         //when

         //then

      }
    /**
     * runtimeException을 상속받은 UncheckedException
     */
    static class MyUncheckedExcption extends RuntimeException{
        public MyUncheckedExcption(String message) {
            super(message);
        }
    }

    /**
     * unChecked예외는 예외를 잡거나 던지지않아도된다.
     * 안잡으면 자동으로 밖으로 던진다.
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * 필요경우 예외를 잡아서 처리하면 된다.
         */
        public void callCatch() {
            try {
                repository.call(); //이렇게만 떠도 에러 안뜸
            }catch (MyUncheckedExcption e) {
                log.info("error = {}", e.getMessage(), e);
            }

        }

        /**
         * 예외 안잡아도 자연스럽게 상위로 넘어간다.
         */
        public void callThrow() {
            repository.call();
        }

    }
    static class Repository {
        public void call() {
            throw new MyUncheckedExcption("ex"); //throws 안던져도 생략가능함(unchecked만)
        }
    }
}
