package com.flz.rm.sb.pullconsumer.controller;

import com.flz.rm.sb.pullconsumer.service.ConsumerService;
import com.flz.rm.sb.shared.dto.Result;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/consumers")
public class ConsumerController {
    private final ConsumerService consumerService;

    @GetMapping("/pull/assign")
    public Result consumeWithAssign() throws MQClientException {
        List<String> messages = consumerService.pullWithAssign();
        return Result.of(messages);
    }

    @GetMapping("/pull/subscribe")
    public Result consumeWithSubscribe() {
        List<String> messages = consumerService.pullWithSubscribe();
        return Result.of(messages);
    }
}
