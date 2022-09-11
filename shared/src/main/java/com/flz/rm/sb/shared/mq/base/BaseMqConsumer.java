package com.flz.rm.sb.shared.mq.base;

import com.flz.rm.sb.shared.mq.base.dto.BaseMessage;
import com.flz.rm.sb.shared.mq.base.scene.MqScene;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseMqConsumer<T extends BaseMessage> {
    protected String topic;
    protected String tag;
    protected DefaultMQPushConsumer pushConsumer;

    public abstract boolean listen(MqScene scene);

    public abstract void doBusiness(T messageDTO);
}
