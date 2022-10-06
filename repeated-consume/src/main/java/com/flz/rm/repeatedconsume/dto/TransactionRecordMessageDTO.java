package com.flz.rm.repeatedconsume.dto;

import com.flz.rm.sb.shared.mq.dto.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TransactionRecordMessageDTO extends BaseMessage {
    private BigDecimal amount;
    private String deliveryLineId;
    private LocalDateTime recordTime;
}
