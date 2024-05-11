package hello.core.singleton;

/**
 * static 영역에 instance를 미리 생성해서
 * 필요시 getInstance()로 조회
 */
public class SingletonService {
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService(){

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
