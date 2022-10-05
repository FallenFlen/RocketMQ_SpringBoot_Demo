package com.flz.rm.repeatedconsume.dto;

import com.flz.rm.sb.shared.mq.dto.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TestMessageDTO extends BaseMessage {
    private String mark;
}
