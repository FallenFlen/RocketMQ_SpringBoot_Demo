package com.flz.rm.sb.shared.mq.base.config;

import com.flz.rm.sb.shared.mq.base.BaseMqConsumer;
import com.flz.rm.sb.shared.mq.base.dto.BaseMessage;
import com.flz.rm.sb.shared.mq.base.properties.MqProperties;
import com.flz.rm.sb.shared.mq.base.scene.MqScene;
import com.flz.rm.sb.shared.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
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
        Map<String, BaseMqConsumer> beanMap = applicationContext.getBeansOfType(BaseMqConsumer.class);
        if (CollectionUtils.isEmpty(beanMap)) {
            return;
        }

        beanMap.values().forEach(consumer -> {
            try {
                initConsumer(consumer);
            } catch (MQClientException e) {
                log.error("init consumer failed, {}", e);
                throw new RuntimeException(e);
            }
        });
        log.info("init {} base mq consumers successfully", beanMap.values().size());
    }

    private void initConsumer(BaseMqConsumer consumer) throws MQClientException {
        String consumerGroup = mqProperties.getConsumerGroup();
        if (StringUtils.isBlank(consumerGroup)) {
            throw new MQClientException(-1, "consumer group can not be blank");
        }

        String nameServer = mqProperties.getNameServer();
        if (StringUtils.isBlank(nameServer)) {
            throw new MQClientException(-1, "name server can not be blank");
        }

        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer();
        pushConsumer.setConsumeThreadMin(mqProperties.getConsumeThreadMin());
        pushConsumer.setConsumeThreadMax(mqProperties.getConsumeThreadMax());
        pushConsumer.setConsumeMessageBatchMaxSize(mqProperties.getMaxConsumeBatchSize());
        pushConsumer.setConsumerGroup(consumerGroup);
        pushConsumer.setNamesrvAddr(nameServer);

        for (MqScene scene : MqScene.values()) {
            if (consumer.listen(scene)) {
                try {
                    pushConsumer.subscribe(scene.getTopic(), scene.getTag());
                } catch (MQClientException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (!ObjectUtils.allNotNull(consumer.getTopic(), consumer.getTag())) {
            throw new MQClientException(-1, "consumer must subscribe topic and tag");
        }

        pushConsumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            try {
                BaseMessage baseMessage = msgs.stream()
                        .map(Message::getBody)
                        .map((body) -> JsonUtils.cast(body, BaseMessage.class))
                        .findFirst()
                        .get();
                consumer.doBusiness(baseMessage);
            } catch (Exception e) {
                log.info("consume message failed : {}", e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        pushConsumer.start();
        consumer.setPushConsumer(pushConsumer);
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
        defaultMQProducer.setRetryTimesWhenSendFailed(mqProperties.getRetryTimesWhenSendFailed());
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(mqProperties.getRetryTimesWhenSendFailed());
        defaultMQProducer.setMaxMessageSize(mqProperties.getMaxMessageSize());
        defaultMQProducer.setSendMsgTimeout(mqProperties.getSendMessageTimeout());
        defaultMQProducer.start();
        log.info("default mq producer started");
        return defaultMQProducer;
    }
}
