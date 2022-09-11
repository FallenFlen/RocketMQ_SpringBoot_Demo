package com.flz.rm.sb.shared.mq.base.config;

import com.flz.rm.sb.shared.mq.base.BaseMqConsumer;
import com.flz.rm.sb.shared.mq.base.properties.MqProperties;
import com.flz.rm.sb.shared.mq.base.scene.MqScene;
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
        Map<String, BaseMqConsumer> beanMap = applicationContext.getBeansOfType(BaseMqConsumer.class);
        if (CollectionUtils.isEmpty(beanMap)) {
            return;
        }

        beanMap.values().forEach(this::initConsumer);
        log.info("init {} base mq consumers successfully", beanMap.values().size());
    }

    private void initConsumer(BaseMqConsumer consumer) {
        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer();
        pushConsumer.setConsumeThreadMin(mqProperties.getConsumeThreadMin());
        pushConsumer.setConsumeThreadMax(mqProperties.getConsumeThreadMax());
        pushConsumer.setConsumerGroup(mqProperties.getConsumerGroup());
        consumer.setPushConsumer(pushConsumer);

        for (MqScene scene : MqScene.values()) {
            if (consumer.listen(scene)) {
                try {
                    consumer.getPushConsumer().subscribe(scene.getTopic(), scene.getTag());
                } catch (MQClientException e) {
                    log.error("init consumer failed, {}", e);
                    throw new RuntimeException(e);
                }
            }
        }

        if (consumer.getTag() == null) {
            consumer.setTag(mqProperties.getDefaultTag());
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
        defaultMQProducer.setRetryTimesWhenSendFailed(mqProperties.getRetryTimesWhenSendFailed());
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(mqProperties.getRetryTimesWhenSendFailed());
        defaultMQProducer.setMaxMessageSize(mqProperties.getMaxMessageSize());
        defaultMQProducer.setSendMsgTimeout(mqProperties.getSendMessageTimeout());
        defaultMQProducer.start();
        log.info("default mq producer started");
        return defaultMQProducer;
    }
}
