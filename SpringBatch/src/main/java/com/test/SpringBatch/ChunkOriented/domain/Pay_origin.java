package com.test.SpringBatch.ChunkOriented.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Pay_origin {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;
    private String tx_name;
    private LocalDateTime tx_date_time;

    public Pay_origin(Long amount, String txName, LocalDateTime txDateTime) {
        this.amount = amount;
        this.tx_name = txName;
        this.tx_date_time = txDateTime;
    }

    public Pay_origin(Long id, Long amount, String txName, LocalDateTime txDateTime) {
        this.id = id;
        this.amount = amount;
        this.tx_name = txName;
        this.tx_date_time = txDateTime;
    }
}
