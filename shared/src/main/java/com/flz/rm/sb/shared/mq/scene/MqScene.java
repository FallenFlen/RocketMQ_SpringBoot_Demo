package com.flz.rm.sb.shared.mq.scene;

public interface MqScene {
    interface TestScene {
        String topic = "test-topic";
        String tag = "test-tag";
    }
}
