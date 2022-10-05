package com.flz.rm.sb.shared.mq.base.config;

import com.flz.rm.sb.shared.mq.base.BaseMqConsumer;
import com.flz.rm.sb.shared.mq.base.properties.MqProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Map;

@Slf4j
@Configuration
@EnableConfigurationProperties(value = {MqProperties.class})
@RequiredArgsConstructor
public class MqBaseConfig {
    private final MqProperties mqProperties;
    private final ApplicationContext applicationContext;

    @PostConstruct
    public void initAllConsumers() {
        Map<String, BaseMqConsumer> baseMqConsumerMap = applicationContext.getBeansOfType(BaseMqConsumer.class);
        if (CollectionUtils.isEmpty(baseMqConsumerMap)) {
            return;
        }

        mqProperties.getConsumers().forEach((configConsumer) -> {
            baseMqConsumerMap.values().stream()
                    .filter((baseMqConsumer) -> baseMqConsumer.listen(configConsumer.getTag()))
                    .forEach((baseMqConsumer) -> initConsumer(baseMqConsumer, configConsumer));
        });
        log.info("{} consumer(s) init", mqProperties.getConsumers().size());
    }

    private void initConsumer(BaseMqConsumer baseMqConsumer, MqProperties.Consumer configConsumer) {
        String consumerGroup = mqProperties.getConsumerGroup();
        if (StringUtils.isBlank(consumerGroup)) {
            throw new RuntimeException("consumer group can not be blank");
        }

        String nameServer = mqProperties.getNameServer();
        if (StringUtils.isBlank(nameServer)) {
            throw new RuntimeException("name server can not be blank");
        }

        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer();
        pushConsumer.setConsumeThreadMin(mqProperties.getConsumeThreadMin());
        pushConsumer.setConsumeThreadMax(mqProperties.getConsumeThreadMax());
        pushConsumer.setConsumeMessageBatchMaxSize(mqProperties.getMaxConsumeBatchSize());
        pushConsumer.setConsumerGroup(consumerGroup);
        pushConsumer.setNamesrvAddr(nameServer);
        pushConsumer.registerMessageListener(baseMqConsumer);
        pushConsumer.setMaxReconsumeTimes(configConsumer.getMaxReconsumeTimes());
        try {
            pushConsumer.subscribe(configConsumer.getTopic(), configConsumer.getTag());
            pushConsumer.start();
        } catch (Exception e) {
            throw new RuntimeException("consumer start failed");
        }
    }

    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        String producerGroup = mqProperties.getProducerGroup();
        if (StringUtils.isBlank(producerGroup)) {
            throw new MQClientException(-1, "producer group can not be blank");
        }

        String nameServer = mqProperties.getNameServer();
        if (StringUtils.isBlank(nameServer)) {
            throw new MQClientException(-1, "name server can not be blank");
        }

        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(producerGroup);
        defaultMQProducer.setNamesrvAddr(nameServer);
        defaultMQProducer.setMaxMessageSize(mqProperties.getMaxMessageSize());
        defaultMQProducer.setSendMsgTimeout(mqProperties.getSendMessageTimeout());
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(mqProperties.getMaxResendTimes());
        defaultMQProducer.start();
        log.info("default mq producer started");
        return defaultMQProducer;
    }
}
