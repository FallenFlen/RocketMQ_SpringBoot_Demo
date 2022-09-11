package com.flz.rm.sb.pullconsumer.service;

import com.flz.rm.sb.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final RocketMQTemplate rocketMQTemplate;
    @Value("${rocketmq.topics.common-topic}")
    private String topic;

    public void send(String content) {
        Message message = new Message(topic, content.getBytes());
        SendResult sendResult = rocketMQTemplate.syncSend(topic, message);
        SendStatus sendStatus = sendResult.getSendStatus();
        if (sendStatus != SendStatus.SEND_OK) {
            throw new BusinessException("sent message failed with status: ".concat(sendStatus.name()));
        }
    }
}
