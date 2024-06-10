package hello.jdbc.repository.ex;

/**
 * db예외를 커스텀
 * jdbc에 종속적이지 않아서 다른 특정기술에 종속적이지않음
 */
public class MyDuplicationKeyException extends MyDbException{
    public MyDuplicationKeyException() {
        super();
    }

    public MyDuplicationKeyException(String message) {
        super(message);
    }

    public MyDuplicationKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDuplicationKeyException(Throwable cause) {
        super(cause);
    }
}
