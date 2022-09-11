package com.flz.rm.sb.pullconsumer.service;

import com.flz.rm.sb.shared.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerService {

    @Qualifier("pullConsumerWithSubscribe")
    private final DefaultLitePullConsumer pullConsumer;
    @Qualifier("pullConsumerWithAssign")
    private final DefaultLitePullConsumer pullConsumerWithAssign;
    @Value("${rocketmq.topics.common-topic}")
    private String topic;

    public List<String> pullWithAssign() throws MQClientException {
        // 从该topic中获取所属的mq，然后从手动选择mq中的消息进行消费
        Collection<MessageQueue> messageQueues = pullConsumerWithAssign.fetchMessageQueues(topic);
        List<MessageQueue> mqList = new ArrayList<>(messageQueues);
        List<MessageQueue> currentConsumingMqLIst = new ArrayList<>();
        for (int i = 0; i < mqList.size() / 2; i++) {
            currentConsumingMqLIst.add(mqList.get(0));
        }
        // 选择本次pull需要从哪些mq中pull消息，这里业务选择了从该topic下一半的mq拉消息
        pullConsumerWithAssign.assign(currentConsumingMqLIst);
        // seek，将获取mq的点位跳到下一班，下次pull的时候拉取下一半mq
        pullConsumerWithAssign.seek(currentConsumingMqLIst.get(0), mqList.size() / 2);

        return pull(pullConsumerWithAssign);
    }

    public List<String> pullWithSubscribe() {
        return pull(pullConsumer);
    }

    private List<String> pull(DefaultLitePullConsumer consumer) {
        List<MessageExt> messageExtList = consumer.poll();
        log.info("pull messages : {}", JsonUtils.silentMarshal(messageExtList));
        return messageExtList.stream()
                .map(MessageExt::getBody)
                .map(String::new)
                .map((json) -> JsonUtils.cast(json, Message.class))// message ext的body转为String后，其实是Message类的json形式
                .map(Message::getBody)
                .map(String::new)// message的body转为String后，才是真正发送的消息（字符串）
                .collect(Collectors.toList());
    }
}
