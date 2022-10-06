package com.flz.rm.repeatedconsume.dto;

import com.flz.rm.sb.shared.mq.scene.MqScene;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRecordDTO {
    private BigDecimal amount;
    private String deliveryLineId;

    public TransactionRecordMessageDTO toMessageDTO() {
        return TransactionRecordMessageDTO.builder()
                .topic(MqScene.TestScene.topic)
                .tag(MqScene.TestScene.tag)
                .recordTime(LocalDateTime.now())
                .amount(this.amount)
                .deliveryLineId(this.deliveryLineId)
                .build();
    }
}
