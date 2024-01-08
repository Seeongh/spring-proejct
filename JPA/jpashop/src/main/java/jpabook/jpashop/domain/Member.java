package jpabook.jpashop.domain;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id  @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded //내장 타입이다.
    private Address address;

    @OneToMany(mappedBy = "member") //주인이 아니고 order테이블에 있는 member의  거울이에요 = readonly
    private List<Order> orders = new ArrayList<>(); //초기화 고민하지 않아도됨, null문제에서 안전해짐
    //hibernate는 엔티티 영속화 할떄 컬렉션을 한번 감싸면서 내장 컬렉션으로 변경됨(hibernate가 추적해야해서)
    //가급적 변경하면 안됨

//    public Member() {
//        orders = new ArrayList<>();
//    }


}
