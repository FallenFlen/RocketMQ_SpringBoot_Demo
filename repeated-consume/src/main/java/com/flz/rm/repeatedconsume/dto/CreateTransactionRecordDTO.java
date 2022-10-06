package com.flz.rm.repeatedconsume.dto;

import com.flz.rm.sb.shared.mq.scene.MqScene;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
                .amount(this.amount)
                .deliveryLineId(this.deliveryLineId)
                .build();
    }
}
