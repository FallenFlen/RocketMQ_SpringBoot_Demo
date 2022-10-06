package com.flz.rm.repeatedconsume.repository;

import com.flz.rm.sb.shared.entity.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, String> {
    boolean existsByDeliveryLineId(String deliveryLineId);
}
