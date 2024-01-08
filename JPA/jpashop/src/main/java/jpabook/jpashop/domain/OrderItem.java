package jpabook.jpashop.domain;

import jpabook.jpashop.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count;  //주문 수량

//    protected OrderItem() { //JPA는 protected까지 기본 생성자 만들 수 있게 허용해줌
//        //이렇게 함으로 new OrderItem() , setter사용하는 방식을 막을 수 있음
        // = @NoArgsConstructor(access= AccessLevel.PROTECTED)
//    }

    //==생성메소드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); //재고까주기
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStock(count); //재고수량 원복
    }

    //==조회로직==//

    /**
     * 주문상품 전체 가격조회
     * @return
     */
    public int getTotalPrice() {
        return getOrderPrice() *getCount();
    }
}
