package com.flz.rm.sb.dead.service;

import com.flz.rm.sb.dead.mqhandler.MessageSendCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqService {
    private final RocketMQTemplate template;
    private final MessageSendCallback messageSendCallback;
    @Value("${rocketmq.topics.common-topic}")
    private String topic;

    public void sendOrderCancelDelayMessage(String id) {
        log.info("send order cancel delay message, order id is {}", id);
        Message message = new Message(topic, id.getBytes());
        message.setDelayTimeLevel(5); // 一分钟
        template.asyncSend(topic, message, messageSendCallback);
    }
}
