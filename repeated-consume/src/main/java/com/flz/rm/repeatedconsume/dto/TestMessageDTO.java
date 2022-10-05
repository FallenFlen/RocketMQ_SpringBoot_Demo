package com.flz.rm.repeatedconsume.dto;

import com.flz.rm.sb.shared.mq.base.dto.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestMessageDTO extends BaseMessage {
    private String mark;
}
