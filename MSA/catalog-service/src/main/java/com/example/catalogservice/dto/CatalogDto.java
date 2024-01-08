package com.example.catalogservice.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class CatalogDto implements Serializable { //직렬화가 뭔데

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
