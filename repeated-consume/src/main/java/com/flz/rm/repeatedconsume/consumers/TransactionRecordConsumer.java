package com.flz.rm.repeatedconsume.consumers;

import com.flz.rm.repeatedconsume.dto.TransactionRecordMessageDTO;
import com.flz.rm.repeatedconsume.repository.TransactionRecordRepository;
import com.flz.rm.sb.shared.entity.TransactionRecord;
import com.flz.rm.sb.shared.mq.scene.MqScene;
import com.flz.rm.sb.shared.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.group}",
        topic = MqScene.TestScene.topic, selectorExpression = MqScene.TestScene.tag)
@RequiredArgsConstructor
public class TransactionRecordConsumer implements RocketMQListener<TransactionRecordMessageDTO> {
    private final TransactionRecordRepository transactionRecordRepository;

    @Override
    public synchronized void onMessage(TransactionRecordMessageDTO message) {
        log.info("received message:{}", JsonUtils.silentMarshal(message));
        if (transactionRecordRepository.existsByDeliveryLineId(message.getDeliveryLineId())) {
            throw new RuntimeException("message consume repeatedly");
        }

        TransactionRecord transactionRecord = TransactionRecord.builder()
                .amount(message.getAmount())
                .deliveryLineId(message.getDeliveryLineId())
                .recordTime(LocalDateTime.now())
                .build();
        transactionRecordRepository.save(transactionRecord);
    }
}
