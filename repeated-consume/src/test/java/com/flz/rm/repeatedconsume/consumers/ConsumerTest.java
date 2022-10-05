package com.flz.rm.repeatedconsume.consumers;

import com.flz.rm.repeatedconsume.service.MqService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConsumerTest {
    @Autowired
    private MqService mqService;

    @Test
    void should_receive_message() {
        mqService.sendTestMessage("mark-abc");
    }
}
