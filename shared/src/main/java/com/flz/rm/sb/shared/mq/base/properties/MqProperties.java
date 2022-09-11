package com.flz.rm.sb.shared.mq.base.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "rocketmq")
public class MqProperties {
    private String consumerGroup;
    private String nameServer;
    private String producerGroup;
    private Integer retryTimesWhenSendFailed;
    private Integer sendMessageTimeout;
    private Integer maxMessageSize;
    private String defaultTag;
    private Integer consumeThreadMin;
    private Integer consumeThreadMax;
    private Integer maxConsumeBatchSize;
}
