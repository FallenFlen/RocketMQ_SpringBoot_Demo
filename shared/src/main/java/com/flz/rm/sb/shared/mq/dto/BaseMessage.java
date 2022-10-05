package com.flz.rm.sb.shared.mq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseMessage {
    private String topic;
    private String tag;

    public String toDestination() {
        return String.format("%s:%s", topic, tag);
    }
}
