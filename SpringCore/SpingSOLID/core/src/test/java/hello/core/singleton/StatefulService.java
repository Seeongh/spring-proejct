package hello.core.singleton;

import org.junit.Test;

/**
 * 싱글톤 패터이기때문에 statefull하면 안된다.
 */
public class StatefulService {

    private int price; //상태를 유지하는 필드
    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price; //여기가 문제
    }

    public int getPrice() {
        return price;
    }
}
