package com.flz.rm.repeatedconsume.consumers;

import com.flz.rm.repeatedconsume.dto.TransactionRecordMessageDTO;
import com.flz.rm.sb.shared.mq.scene.MqScene;
import com.flz.rm.sb.shared.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.group}",
        topic = MqScene.TestScene.topic, selectorExpression = MqScene.TestScene.tag)
public class TransactionRecordConsumer implements RocketMQListener<TransactionRecordMessageDTO> {

    @Override
    public void onMessage(TransactionRecordMessageDTO message) {
        log.info("received message:{}", JsonUtils.silentMarshal(message));
    }
}
