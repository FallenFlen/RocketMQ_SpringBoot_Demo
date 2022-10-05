package com.flz.rm.sb.shared.mq.base.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "rocketmq")
public class MqProperties {
    private String consumerGroup;
    private String nameServer;
    private String producerGroup;
    private Integer sendMessageTimeout;
    private Integer maxMessageSize;
    private Integer consumeThreadMin;
    private Integer consumeThreadMax;
    private Integer maxConsumeBatchSize;
    private Integer maxResendTimes;
    private List<Consumer> consumers;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Consumer {
        private String tag;
        private String topic;
        private Integer maxReconsumeTimes;
    }
}
