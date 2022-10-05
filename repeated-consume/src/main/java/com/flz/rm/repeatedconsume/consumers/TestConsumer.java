package com.flz.rm.repeatedconsume.consumers;

import com.flz.rm.repeatedconsume.dto.TestMessageDTO;
import com.flz.rm.sb.shared.mq.base.BaseMqConsumer;
import com.flz.rm.sb.shared.mq.base.scene.MqScene;
import com.flz.rm.sb.shared.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestConsumer extends BaseMqConsumer<TestMessageDTO> {
    @Override
    public boolean listen(String tag) {
        return MqScene.TEST_SCENE.getTag().equals(tag);
    }

    @Override
    public void doBusiness(TestMessageDTO messageDTO) {
        log.info("received message:{}", JsonUtils.silentMarshal(messageDTO));
    }
}
