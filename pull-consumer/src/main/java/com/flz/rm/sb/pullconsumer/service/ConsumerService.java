package com.flz.rm.sb.pullconsumer.service;

import com.flz.rm.sb.shared.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerService {

    @Qualifier("pullConsumerWithSubscribe")
    private final DefaultLitePullConsumer pullConsumer;

    public List<String> pull() {
        List<MessageExt> messageExtList = pullConsumer.poll();
        log.info("pull messages : {}", JsonUtils.silentMarshal(messageExtList));

        return messageExtList.stream()
                .map(MessageExt::getBody)
                .map(String::new)
                .collect(Collectors.toList());
    }
}
