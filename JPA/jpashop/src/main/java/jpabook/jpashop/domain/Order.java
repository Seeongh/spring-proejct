package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
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
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL) //order저장시 delivery도 persist해줌
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
}
