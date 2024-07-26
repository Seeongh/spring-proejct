package com.test.SpringBatch.ChunkStudy.repository;

import com.test.SpringBatch.ChunkStudy.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
