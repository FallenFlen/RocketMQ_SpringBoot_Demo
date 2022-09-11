package com.flz.rm.sb.dead.mqhandler;

import com.flz.rm.sb.shared.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageSendCallback implements SendCallback {
    @Override
    public void onSuccess(SendResult sendResult) {
        log.info("send message successfully ： {}", JsonUtils.silentMarshal(sendResult));
    }

    @Override
    public void onException(Throwable e) {
        log.error("send message failed with {}", e);
    }
}
