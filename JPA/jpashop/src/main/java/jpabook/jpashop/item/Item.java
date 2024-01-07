package jpabook.jpashop.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속관계 매핑시는 전략을 부모클래스에 잡아주어야함
//joined = 정규화된 스타일, single table은 한테이블에 다 넣음 , table per class는 각 각 테이블이 나옴
@DiscriminatorColumn(name = "dtype") //book이면 어떻게 할건지
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name= "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직 ==//
    //엔티티자체가 해결할 수 있는건 안에서 해주는게 나음
    //setter를 인쓰고 이걸로 +- 해줌

    /**
     * stock 증가
     * @param quantity
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity; //재고 수량 증가
    }

    /**
     * stock 감소
     * @param quantity
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if(restStock <0 ) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
