package com.flz.rm.sb.shared.entity;

import com.flz.rm.sb.shared.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_transaction_record")
public class TransactionRecord extends BaseEntity {
    private BigDecimal amount;
    private LocalDateTime recordTime;
    private String deliveryLineId;
}
