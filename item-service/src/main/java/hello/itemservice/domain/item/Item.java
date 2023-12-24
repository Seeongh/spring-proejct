package hello.itemservice.domain.item;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.예측하지 못하는 경우가 많아 다 알고써야함
//@Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price; //가격에 null이 들어갈 수 있어서 Integer로 선언 price 0과 null은 다른의미
    private Integer quantity; //수량의 경우 0이들어가도 되지만 그냥 통일해줌

    public Item() {// 기본 생성자
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
