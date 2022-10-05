package com.flz.rm.repeatedconsume.service;

import com.flz.rm.repeatedconsume.dto.TestMessageDTO;
import com.flz.rm.sb.shared.mq.base.scene.MqScene;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MqService {
    private final RocketMQTemplate rocketMQTemplate;

    public void sendTestMessage(String mark) {
        TestMessageDTO messageDTO = TestMessageDTO.builder()
                .mark(mark)
                .build();
        messageDTO.setTopic(MqScene.TEST_SCENE.getTopic());
        messageDTO.setTags(MqScene.TEST_SCENE.getTag());
        rocketMQTemplate.sendOneWay(messageDTO.toDestination(), messageDTO);
    }
}
