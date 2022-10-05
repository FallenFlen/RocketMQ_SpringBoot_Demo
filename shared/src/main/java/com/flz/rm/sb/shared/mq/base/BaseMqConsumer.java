package com.flz.rm.sb.shared.mq.base;

import com.flz.rm.sb.shared.mq.base.dto.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseMqConsumer<T extends BaseMessage> implements MessageListenerConcurrently {
    public abstract boolean listen(String tag);

    public abstract void doBusiness(T messageDTO);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        return null;
    }
}
