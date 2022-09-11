package com.flz.rm.sb.pullconsumer.config;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
    @Value("${rocketmq.topics.common-topic}")
    private String topic;
    @Value("${rocketmq.name-server}")
    private String nameServer;
    @Value("${rocketmq.consumer.pull-batch-size}")
    private int pullBatchSize;

    @Value("${rocketmq.consumer.group}")
    private String defaultConsumerGroupName;

    @Bean
    public DefaultLitePullConsumer pullConsumerWithSubscribe() throws MQClientException {
        DefaultLitePullConsumer defaultLitePullConsumer = new DefaultLitePullConsumer(defaultConsumerGroupName);
        defaultLitePullConsumer.setNamesrvAddr(nameServer);
        defaultLitePullConsumer.subscribe(topic, "*");
        defaultLitePullConsumer.setPullBatchSize(pullBatchSize);
        defaultLitePullConsumer.start();
        return defaultLitePullConsumer;
    }
}
