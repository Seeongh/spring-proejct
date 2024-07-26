package com.test.SpringBatch.ChunkStudy.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long purchaseOrderId;

    @Column
    private String productIdList;

    @Builder
    public History(Long purchaseOrderId, List<Product> productList) {
        this.purchaseOrderId = purchaseOrderId;
        this.productIdList = productList.stream()
                .map(product -> String.valueOf(product.getId()))
                .collect(Collectors.joining(","));
    }
}
