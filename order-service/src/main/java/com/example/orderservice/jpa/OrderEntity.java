package com.example.orderservice.jpa;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name= "orders")
public class OrderEntity implements Serializable { //직렬화 가진 객체를 전송, 보관하기 위해서 마셜링작업,, 뭐여그게

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120 , unique = true)
    private String productId;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false, updatable = false, insertable = false) //초기에 insert안하고 자동생성
    @ColumnDefault(value = "CURRENT_TIMESTAMP") //현재 시간 가지고옴
    private Date createAt;
}
