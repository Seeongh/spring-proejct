package com.test.SpringBatch.ChunkStudy.domain;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product {


    private Long id;

    private String name;

    private long amount;

    @Builder
    public Product(String name, long amount) {
        this.name = name;
        this.amount = amount;
    }
}