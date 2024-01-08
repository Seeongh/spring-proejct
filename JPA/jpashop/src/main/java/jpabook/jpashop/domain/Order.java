package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id") //foreign key
    private Member member;

    /**
     * persist(orderItemA)
     * persist(orderItemB)
     * persist(order)
     * 인 경우에 cascade설정해두면 persist(order)하면됨
     * cascade가 전파해주는 역할이다.
     * 범위 : 딱 여기서만 참조할때 쓸 수있음
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL) //order persist시 delivery도 persist해줌
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //HIBERNATE가 알아서 자동 치환해줌

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 ENUM값 ORDER,CANCEL

    /**
     * 양방향일때 order넣으면 member에도 넣어야함
     * 양쪽에 값 셋팅하는게 best
     * member.getOrders().add(order);
     * order.setMember(member)
     * 이 메서드는 핵심적으로 컨트롤 하는쪽이 드는게 유리
     */
    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this); //이걸로 묶어서 들어가게 해놓음
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery =delivery;
        delivery.setOrder(this);
    }

    //==생성메서드==//
    /**
     * 연관관계가 있는 복잡한생성은 별도의 메소드가 있는게 나음
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order() ;
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스로직==//
    /**
     * 주문취소
     */
    public void cancel() {
        if(delivery.getStatus() ==DeliveryStatus.COMP) {
            //배송완료시
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가합니다");
        }
        this.setStatus(OrderStatus.CANCEL); //변경하면 query를 날려야하는데 데이터만 바꾸면 dirtychecking변경감지해서 db에 업데이트
        for( OrderItem orderItem: orderItems) { //this는 강조할때나 이름같을때 외에는 잘안쓰심
            orderItem.cancel();
        }
    }


    //==조회로직==//

    /**
     * 전체 주문 가격 조회
     * @return
     */
    public int getTotalPrice() {
        int totalPrice =  0 ;
        for( OrderItem orderItem: orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
