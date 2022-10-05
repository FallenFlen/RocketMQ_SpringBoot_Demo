package com.flz.rm.sb.shared.mq.base.dto;

import org.apache.rocketmq.common.message.Message;

public abstract class BaseMessage extends Message {
    public String toDestination() {
        return String.format("%s:%s", getTopic(), getTags());
    }
}
