package com.flz.rm.sb.shared.mq.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.flz.rm.sb.shared.mq.base.dto.BaseMessage;
import com.flz.rm.sb.shared.utils.JsonUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public abstract class BaseMqConsumer<T extends BaseMessage> implements MessageListenerConcurrently {
    public abstract boolean listen(String tag);

    public abstract void doBusiness(T messageDTO);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        msgs.forEach((messageExt) -> {
            TypeReference<T> typeReference = new TypeReference<>() {
            };
            T messageDTO = JsonUtils.cast(messageExt.getBody(), typeReference);
            doBusiness(messageDTO);
        });

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
