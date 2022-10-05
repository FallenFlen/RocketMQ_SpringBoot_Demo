package com.flz.rm.sb.shared.mq.base.scene;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MqScene {
    ORDER_CANCEL_SCENE("order-scene", "order-cancel-scene-tag"),
    TEST_SCENE("test-scene", "test-scene-tag");

    private String topic;
    private String tag;
}
