package com.example.orderservice.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDto implements Serializable { //직렬화가 뭔데

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
