package com.flz.rm.repeatedconsume.service;

import com.flz.rm.repeatedconsume.dto.CreateTransactionRecordDTO;
import com.flz.rm.repeatedconsume.dto.TestMessageDTO;
import com.flz.rm.repeatedconsume.dto.TransactionRecordMessageDTO;
import com.flz.rm.sb.shared.mq.handler.MessageSendCallback;
import com.flz.rm.sb.shared.mq.scene.MqScene;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MqService {
    private final RocketMQTemplate rocketMQTemplate;
    private final MessageSendCallback messageSendCallback;

    public void sendTestMessage(String mark) {
        TestMessageDTO messageDTO = TestMessageDTO.builder()
                .mark(mark)
                .topic(MqScene.TestScene.topic)
                .tag(MqScene.TestScene.tag)
                .build();
        rocketMQTemplate.sendOneWay(messageDTO.toDestination(), messageDTO);
    }

    public void createTransactionRecord(CreateTransactionRecordDTO dto) {
        TransactionRecordMessageDTO messageDTO = dto.toMessageDTO();
        rocketMQTemplate.asyncSend(messageDTO.toDestination(), messageDTO, messageSendCallback);
    }
}
