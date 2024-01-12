package hello.core.singleton;

public class StatelessService {
    private int price; //stateful에서는 이게덮어쓰여지면서 문제가 발생한다.

    //stateful을 stateless로 만드는 법
    // 1. 생성자 -> 생성을 한번만 하니까 안될거같다.
    // 2. getPrice를 하려면 다시 order를 무조건 호출하기 -> 유지보수,,어려움
    // 3. price 초기화 하기 -> 재 조회시 0 원
    // 4. 아, this 가아니라 어떤 메소드로 제공?
    // 5. return price;를 함
    public int order(String name, int price) {
        System.out.println("name : " + name + " price: " + price);
        //this.price = price; // 1번 객체를 다시 참조할떄 1번객체 값이여야한다면...
        return price;
    }

}
